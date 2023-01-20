package com.apple.apple.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.apple.apple.models.dao.IClienteDao;
import com.apple.apple.models.entity.Cliente;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    @Qualifier("clienteDaoJPA")
    private IClienteDao clienteDao;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    private String listar(Model model) {
        model.addAttribute("titulo", "Listado de cliente");
        model.addAttribute("clientes", clienteDao.findAll());
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
    public String guardar(Model model, @Valid Cliente cliente, BindingResult result, SessionStatus status) {
        //BindingResult siempre despues de @Valid y despues de mas parametros
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Cliente");
            model.addAttribute("errors", result.getAllErrors());
            return "form";
        }
        clienteDao.save(cliente);
        status.setComplete();
        return "redirect:listar";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Cliente");
        Cliente cliente = new Cliente();
        if (id>0) {
            cliente = clienteDao.findOne(id);
        } else {
            return "redirect:form";
        }
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        if (id >0){
            clienteDao.delete(id);
        }
        return "redirect:/listar";
    }

}
