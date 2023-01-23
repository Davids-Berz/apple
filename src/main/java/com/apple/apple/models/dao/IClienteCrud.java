package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteCrud extends JpaRepository<Cliente,Long> {
}
