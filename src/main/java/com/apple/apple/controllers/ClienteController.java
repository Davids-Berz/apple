package com.apple.apple.controllers;

import com.apple.apple.service.IClienteService;
import com.apple.apple.service.IUploadFileService;
import com.apple.apple.utils.paginator.PageRender;
import com.apple.apple.view.xml.ClienteList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.apple.apple.models.entity.Cliente;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Controller
@SessionAttributes("cliente")
public class ClienteController {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    @Qualifier("ClienteServiceCrudRepository")
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private MessageSource messageSource;

    @Secured("USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Path pathPhoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
        LOG.info("pathPhoto: " + pathPhoto);
        Resource recurso = null;
        try {
            recurso = new UrlResource(pathPhoto.toUri());
            if (!recurso.exists() && !recurso.isReadable()) {
                throw new RuntimeException("Error: no se puede cargar la imagen");

            }
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping(value = "/api/listar")
    public @ResponseBody List<Cliente> listarRest() {
        return clienteService.findAll();
    }

//    XML + formato JSON
    @GetMapping(value = "/api/listar-xml")
    public @ResponseBody ClienteList listarRestXml() {
        return new ClienteList(clienteService.findAll());
    }

    @RequestMapping(value = {"/", "/listar"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
                         Authentication authentication, HttpServletRequest request,
                         Locale locale) {

        if (authentication != null) {
            LOG.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            LOG.info("Utilizando SecurityContextHolder.getContext().getAuthentication()");
        } else {
            LOG.info("No tienes Acceso");
        }

        if (hasRole("ROLE_ADMIN")) {
            LOG.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
        }

        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");

        if (securityContext.isUserInRole("ADMIN")) {
            LOG.info("Utilizando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            LOG.info("Utilizando SecurityContextHolderAwareRequestWrapper /// No tienes acceso");
        }

        if (request.isUserInRole("ROLE_ADMIN")) {
            LOG.info("Utilizando HttpServletRequest ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            LOG.info("Utilizando HttpServletRequest /// No tienes acceso");
        }

        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    //Cualquiera de las 2 formas (Secured y PreAuthorize)
//    @Secured("ROLE_USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
//        Cliente cliente = clienteService.findOne(id);
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: ".concat(cliente.getNombre()));

        return "ver";
    }

//    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Formulario Cliente");
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(Model model, @RequestParam(name = "pic") MultipartFile foto,
                          @Valid Cliente cliente, BindingResult result,
                          RedirectAttributes flash, SessionStatus status) {
        //BindingResult siempre despues de @Valid y despues de mas parametros
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Cliente");
            model.addAttribute("errors", result.getAllErrors());
            return "form";
        }

        if (!foto.isEmpty()) {

            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
                Path rootPath = Paths.get("uploads").resolve(cliente.getFoto()).toAbsolutePath();
                File file = rootPath.toFile();
                if (file.exists() && file.canRead()) {
                    file.delete();
                }
            }
            String uniqueFilename = foto.getOriginalFilename() + UUID.randomUUID();
            Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
            Path pathAbsolute = rootPath.toAbsolutePath();
            LOG.info("roothPath: " + rootPath);
            LOG.info("pathAbsolute: " + pathAbsolute);
            try {
                Files.copy(foto.getInputStream(), pathAbsolute);
                flash.addFlashAttribute("fotoSuccess", "Foto subida!");
                cliente.setFoto(uniqueFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", "Cliente agregado con exito");
        return "redirect:/listar";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable Long id, RedirectAttributes flash, Model model) {
        model.addAttribute("titulo", "Editar Cliente");
        Cliente cliente = new Cliente();
        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El id del cliente no existe");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El id del cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito");

            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "foto: ".concat(cliente.getNombre()).concat(" eliminada con exito"));
            }
        }
        return "redirect:/listar";
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return false;
        Authentication auth = context.getAuthentication();
        
        if (auth == null) return false;
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));
        /*for (GrantedAuthority authority:
             authorities) {
            if (role.equals(authority.getAuthority())) {
                LOG.info("Hola ".concat(auth.getName()).concat("tu rol es: ").concat(authority.getAuthority()));
                return true;
            }
        }
        return false;*/
    }

}
