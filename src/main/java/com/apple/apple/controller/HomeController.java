package com.apple.apple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        // return "redirect:/app";
        
        //Para redireccionar una pagina de inicio
        return "forward:/app";
    }
}
