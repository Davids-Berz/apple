package com.apple.apple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/listar")
    public String listar(ModelMap map) {

        List<Usuario> uList = new ArrayList<>();
        uList.add(new Usuario("David", "saldivar", "Davids.Berz@gmail.com"));
        uList.add(new Usuario("Jose", "Perez", "JPerez@gmail.com"));
        map.addAttribute("titulo", "Listado de Usuarios");
        // map.addAttribute("usuarios",uList);

        return "listar";
    }

    // Metodo con anotiacion ModelAttribute para el controlador Index
    @ModelAttribute("usuarios")
    public List<Usuario> lstUsuario() {
        List<Usuario> uList = new ArrayList<>();
        uList.add(new Usuario("David", "saldivar", "Davids.Berz@gmail.com"));
        uList.add(new Usuario("Jose", "Perez", "JPerez@gmail.com"));
        return uList;
    }

}
