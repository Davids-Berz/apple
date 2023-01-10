package com.apple.apple.models;

import java.util.List;
import com.apple.apple.models.entity.Usuario;

public interface IUsuarioService {
    
    public List<Usuario> listar();

    public Usuario getId(Integer id);
}
