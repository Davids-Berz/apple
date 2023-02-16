package com.apple.apple.controllers;

import com.apple.apple.models.entity.Cliente;
import com.apple.apple.service.IClienteService;
import com.apple.apple.view.xml.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-rest")
public class ClienteRestController {

    @Autowired
    IClienteService clienteService;

    //    XML + formato JSON
    @GetMapping(value = "/listar-xml")
    public ClienteList listarXml() {
        return new ClienteList(clienteService.findAll());
    }
}
