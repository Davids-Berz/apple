package com.apple.apple.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apple.apple.models.dao.IClienteDao;
import com.apple.apple.models.entity.Cliente;


@Controller
public class ClienteController {
    

    @Autowired
    @Qualifier("clienteDaoJPA")
    private IClienteDao clienteDao;

    @RequestMapping(value="/listar", method = RequestMethod.GET)
    private String listsar(Model model) {
        model.addAttribute("titulo", "Listado de cliente");
        model.addAttribute("clientes", clienteDao.findAll());
        return  "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Formulario Cliente");
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String guardar(Model model, @Valid Cliente cliente, BindingResult result) {
        //BindingResult siempre despues de @Valid y despues de mas parametros
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario Cliente");
            model.addAttribute("errors", result.getAllErrors());
            return "form";
        }
        clienteDao.save(cliente);
        return "redirect:listar";
    }
    
}
