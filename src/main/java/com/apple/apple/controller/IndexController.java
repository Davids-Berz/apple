package com.apple.apple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class IndexController {
    

    @GetMapping({"/",""})
    public String index(Model model) {
        model.addAttribute("titulo", "Creando un Controlador");
        return "index";
    }
}
