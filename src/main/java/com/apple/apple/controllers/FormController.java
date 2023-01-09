package com.apple.apple.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.apple.apple.editors.NombreMayusEditor;
import com.apple.apple.editors.PaisPropertyEditor;
import com.apple.apple.editors.RolesEditor;
import com.apple.apple.models.entity.Pais;
import com.apple.apple.models.entity.Role;
import com.apple.apple.models.entity.Usuario;
import com.apple.apple.services.PaisService;
import com.apple.apple.services.RoleService;
import com.apple.apple.validations.UsuarioValidator;

import jakarta.validation.Valid;

@Controller
@SessionAttributes({"usuario"})
public class FormController {

    @Autowired
    private UsuarioValidator usuarioValid;

    @Autowired
    private PaisService paisService;

    @Autowired
    private RoleService roleService;

    // Roles

    @Autowired
    private PaisPropertyEditor paisPropertyEditor;

    @Autowired
    private RolesEditor rolesEditor;


    // Se usa para desacoplar la validacion dentro de los Endpoint
    @InitBinder
    public void initBinding(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(usuarioValid);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, "fechaNacimiento",
                new CustomDateEditor(formatter, false));

        // registrando nuevo customEditor
        webDataBinder.registerCustomEditor(String.class, "username", new NombreMayusEditor());

        // registrar editor
        webDataBinder.registerCustomEditor(Pais.class, "pais", paisPropertyEditor);

        webDataBinder.registerCustomEditor(Role.class, "roles", rolesEditor);

    }

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises() {
        return paisService.paises();

    }

    @ModelAttribute("roleService")
    public List<Role> roleService() {
        return roleService.roles();
    }

    @ModelAttribute("strRoles")
    public List<String> roles() {
        return List.of("ROLE_ADMIN", "ROLE_USUARIO", "ROLE_MODERATOR");
    }

    @ModelAttribute("mapRoles")
    public Map<String,String> mapRoles() {
        Map<String, String> map = new HashMap<>();
        map.put("ROLE_ADMIN", "Administrador");
        map.put("ROLE_USUARIO", "Usuario");
        map.put("ROLE_MODERATOR", "Moderador");
        return map;
    }

    @ModelAttribute("paises")
    public List<String> paises() {
        return List.of("Argentina", "Australia", "Brasil", "Chile", "Colombia", "Costa Rica", "Dominican Republic",
                "Ecuador", "El Salvador", "Guatemala", "Honduras", "Mexico");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap() {
        Map<String, String> paises = new HashMap<String, String>();
        paises.put("AR", "Argentina");
        paises.put("AU", "Australia");
        paises.put("MX", "Mexico");
        paises.put("CL", "Chile");
        paises.put("CO", "Colombia");
        paises.put("CR", "Costa Rica");
        paises.put("DO", "Dominican Republic");
        paises.put("EC", "Ecuador");
        paises.put("SV", "El Salvador");
        paises.put("GT", "Guatemala");
        paises.put("HN", "Honduras");
        return paises;
    }

    @GetMapping("/form")
    public String form(Model model) {

        Usuario usuario = new Usuario();
        usuario.setId("123-33");
        model.addAttribute("titulo", "Formulario");
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/form")
    public String processForm(Model model,
            @Valid Usuario usuario,
            BindingResult result,
            SessionStatus status) {
        // usuarioValid.validate("usuario", result);
        model.addAttribute("titulo", "Resultado Form");
        // se pueblan los datos teniendo los mismos campos
        // BindingResult siempre despues del @Valid
        // ModelAttr etiqueta el campo usuario por user
        if (result.hasErrors()) {
            return "form";
        }

        model.addAttribute("usuario", usuario);
        // se elimina el usuario de la sesion
        status.setComplete();
        return "resultado";
    }

}
