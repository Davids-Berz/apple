package com.apple.apple.controllers;

import com.apple.apple.models.entity.Cliente;
import com.apple.apple.models.entity.Factura;
import com.apple.apple.models.entity.Producto;
import com.apple.apple.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("factura")
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;
    @GetMapping("/form/{clienteid}")
    public String crear(@PathVariable Long clienteid, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findOne(clienteid);
        if (cliente == null) {
            flash.addFlashAttribute("error","El cliente no existe en la base de datos");
            return "redirect/listar";
        }

        Factura factura = new Factura();

        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo","Crear Factura");

        return "/factura/form";
    }

    @GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> autocomplete(@PathVariable String term) {
        return clienteService.findByName(term);
    }
}
