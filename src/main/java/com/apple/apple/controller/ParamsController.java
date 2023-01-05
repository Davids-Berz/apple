package com.apple.apple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/params")
public class ParamsController {
    
    // Request Params link a params
    @GetMapping("/")
    public String index() {
        return "params/index";
    }
    
    @GetMapping("/string")
    public String params(@RequestParam(name="text", required=false, defaultValue = "Valor por defecto") String text, Model model) {
        model.addAttribute("resultado", "El parametro enviado es: " + text);
        return "params/ver";
    }

    @GetMapping("/mix-params")
    public String params(@RequestParam String nombre, @RequestParam Integer edad, Model model) {
        model.addAttribute("resultado", "nombre " + nombre + " edad " + edad);
        return "params/ver";
    } 

    @GetMapping("/http-params")
    public String params(HttpServletRequest request, Model model) {
        String nombre = request.getParameter("nombre");
        Integer edad = Integer.valueOf(request.getParameter("edad"));
        model.addAttribute("resultado", "nombre " + nombre + " edad " + edad);
        return "params/ver";
    } 
}
