package com.apple.apple.controllers;

import com.apple.apple.models.entity.Cliente;
import com.apple.apple.models.entity.Factura;
import com.apple.apple.models.entity.ItemFactura;
import com.apple.apple.models.entity.Producto;
import com.apple.apple.service.IClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("factura")
@RequestMapping("/factura")
public class FacturaController {

    private static final Logger LOG = LoggerFactory.getLogger(FacturaController.class);

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/form/{clienteid}")
    public String crear(@PathVariable Long clienteid, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findOne(clienteid);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect/listar";
        }

        Factura factura = new Factura();

        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear Factura");

        return "/factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> autocomplete(@PathVariable String term) {
        return clienteService.findByName(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "crear factura");
            LOG.info(result.getAllErrors().toString());
            return "factura/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "crear factura");
            model.addAttribute("error","Error: la Factura no tiene items");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteService.findProductoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            LOG.info("ID: " + itemId[i].toString());
            LOG.info("Cantidad: " + cantidad[i].toString());
        }

        clienteService.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con exito");

        return "redirect:/ver/" + factura.getCliente().getId();
    }
}
