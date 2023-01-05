package com.apple.apple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apple.apple.models.Usuario;

@Controller
@RequestMapping("/app")
public class IndexController {
    

    @GetMapping({"/",""})
    public String index(Model model) {
        model.addAttribute("titulo", "Creando un Controlador");
        return "index";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap map) {
        final Usuario usuario = new Usuario();
        usuario.setNombre("David");
        usuario.setApellido("Saldivar");
        map
                .addAttribute("usuario", usuario)
                .addAttribute("titulo", "Usuario: " + usuario.getNombre());
        return "perfil";
    }
}
