package com.apple.apple.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping("/")
    public String index(Model model) {
        Integer i = 100/0;
        return "index";
    }

    @GetMapping("/numero")
    public String numero(Model model) {
        Integer i = Integer.parseInt("10k");
        return "index";
    }
}
