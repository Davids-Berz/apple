package com.apple.apple.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping({"/",""})
    public String index(Model model) {
        model.addAttribute("titulo","Horario de atencion a clientes");
        return "index";
    }

    @GetMapping({"/cerrado"})
    public String cerrado(Model model) {
        model.addAttribute("titulo","Horario de atencion a clientes");
        model.addAttribute("cerrado","Fuera de horario de atencion a clientes");
        return "cerrado";
    }

}
