package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioCrud extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
}
