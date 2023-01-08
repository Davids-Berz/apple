package com.apple.apple.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class FormController {
    
    @GetMapping("form")
    public String form(Model model) {
        return "form";
    }

    @PostMapping(value="form")
    public String processForm(Model model) {
        //TODO: process POST request
        
        return "resultado";
    }
    
}
