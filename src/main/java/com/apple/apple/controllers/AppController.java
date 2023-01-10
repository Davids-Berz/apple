package com.apple.apple.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.apple.apple.errors.UsuarioException;
import com.apple.apple.models.IUsuarioService;
import com.apple.apple.models.entity.Usuario;

@Controller
public class AppController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public String index(Model model) {
        // Integer i = 100/0;
        return "index";
    }

    @GetMapping("/numero")
    public String numero(Model model) {
        // Integer i = Integer.parseInt("10k");
        return "index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.getId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Detalle de usuario: "+ usuario.getNombre());
        return "ver";
    }
}
