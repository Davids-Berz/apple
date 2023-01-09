package com.apple.apple.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.apple.apple.models.entity.Usuario;

@Component
public class UsuarioValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.usuario.nombre");

        ValidationUtils.rejectIfEmpty(errors, "apellido", "NotEmpty.usuario.apellido");

        ValidationUtils.rejectIfEmpty(errors, "email", "NotEmpty.usuario.email");
        
        ValidationUtils.rejectIfEmpty(errors, "password", "NotEmpty.usuario.password");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaNacimiento", "typeMismatch.java.util.Date");
    }
    

}
