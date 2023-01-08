package com.apple.apple.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apple.apple.models.entity.Usuario;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FormController {

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Formulario");
        return "form";
    }

    @PostMapping("/form")
    public String processForm(Model model, Usuario usuario) {
        // se pueblan los datos teniendo los mismos campos
        model.addAttribute("usuario", usuario)
                .addAttribute("titulo", "Resultado Form");
        return "resultado";
    }

}
