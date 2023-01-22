package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteCrud extends CrudRepository<Cliente,Long> {
}
