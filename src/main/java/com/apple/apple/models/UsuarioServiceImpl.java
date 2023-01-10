package com.apple.apple.models;

import java.util.List;
import org.springframework.stereotype.Service;

import com.apple.apple.errors.UsuarioException;
import com.apple.apple.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private List<Usuario> usuarios = List
            .of(new Usuario(1, "David", "Saldivar"),
                    new Usuario(2, "Jose", "Andres"),
                    new Usuario(3, "Andrea", "Gzz"));

    @Override
    public Usuario getId(Integer id) {
        return usuarios.stream()
        .filter(input -> input.getId() == id)
        .findFirst()
        .orElseThrow(() -> new UsuarioException(id.toString()));
    }

    @Override
    public List<Usuario> listar() {
        return usuarios;
    }

}
