package com.apple.apple.controllers;

import com.apple.apple.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.apple.apple.models.entity.Cliente;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    @Qualifier("ClienteServiceCrudRepository")
    private IClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    private String listar(Model model) {
        model.addAttribute("titulo", "Listado de cliente");
        model.addAttribute("clientes", clienteService.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Formulario Cliente");
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(Model model, @Valid Cliente cliente, BindingResult result, RedirectAttributes flash, SessionStatus status) {
        //BindingResult siempre despues de @Valid y despues de mas parametros
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Cliente");
            model.addAttribute("errors", result.getAllErrors());
            return "form";
        }
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", "Cliente agregado con exito");
        return "redirect:/listar";
    }

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

    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

        if (id > 0) {
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito");
        }
        return "redirect:/listar";
    }

}
