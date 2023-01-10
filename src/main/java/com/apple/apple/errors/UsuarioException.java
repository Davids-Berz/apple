package com.apple.apple.errors;

public class UsuarioException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public UsuarioException(String id) {
        super("Usuario con id: ".concat(id).concat(" no existe en el sistema") );
    }
    
}
