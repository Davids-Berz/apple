package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao  extends JpaRepository<Cliente, Long> {
}
