package com.apple.apple.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apple.apple.models.entity.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class FormController {

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Formulario");
        return "form";
    }

    @PostMapping("/form")
    public String processForm(Model model, @Valid Usuario usuario, BindingResult result) {
        // se pueblan los datos teniendo los mismos campos
        // BindingResult siempre despues del @Valid
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap();
            result.getFieldErrors().forEach(s -> {

                errors.put(s.getField(), "El campo " + s.getField() + " " + s.getDefaultMessage());
            });
            model.addAttribute("error", errors);
            return "form";
        }

        model.addAttribute("usuario", usuario)
                .addAttribute("titulo", "Resultado Form");
        return "resultado";
    }

}
