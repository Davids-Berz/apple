package com.apple.apple.controllers;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.apple.apple.editors.NombreMayusEditor;
import com.apple.apple.models.entity.Usuario;
import com.apple.apple.validations.UsuarioValidator;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidator usuarioValid;

    // Se usa para desacoplar la validacion dentro de los Endpoint
    @InitBinder
    public void initBinding(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(usuarioValid);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, "fechaNacimiento",new CustomDateEditor(formatter, false));

        // registrando nuevo customEditor
        webDataBinder.registerCustomEditor(String.class, "username", new NombreMayusEditor());

    }

    @GetMapping("/form")
    public String form(Model model) {

        Usuario usuario = new Usuario();
        usuario.setId("123-33");
        model.addAttribute("titulo", "Formulario");
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/form")
    public String processForm(Model model,
            @Valid Usuario usuario,
            BindingResult result,
            SessionStatus status) {
        //usuarioValid.validate("usuario", result);
        model.addAttribute("titulo", "Resultado Form");
        // se pueblan los datos teniendo los mismos campos
        // BindingResult siempre despues del @Valid
        // ModelAttr etiqueta el campo usuario por user
        if (result.hasErrors()) {
            return "form";
        }

        model.addAttribute("usuario", usuario);
        // se elimina el usuario de la sesion
        status.setComplete();
        return "resultado";
    }

}
