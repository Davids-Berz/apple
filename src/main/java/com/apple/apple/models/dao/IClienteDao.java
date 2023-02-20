package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao  extends CrudRepository<Cliente, Long> {
}
