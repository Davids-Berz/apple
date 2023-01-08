package com.apple.apple.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.apple.apple.models.entity.Usuario;
import jakarta.validation.Valid;

@Controller
public class FormController {

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Formulario");
        model.addAttribute("usuario", new Usuario());
        return "form";
    }

    @PostMapping("/form")
    public String processForm(Model model,
            @Valid Usuario usuario, 
            BindingResult result) {
        model.addAttribute("titulo", "Resultado Form");
        // se pueblan los datos teniendo los mismos campos
        // BindingResult siempre despues del @Valid
        // ModelAttr etiqueta el campo usuario por user
        if (result.hasErrors()) {
            return "form";
        }

        model.addAttribute("usuario", usuario);

        return "resultado";
    }

}
