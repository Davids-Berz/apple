package com.apple.apple.models.dao;

import java.util.List;

import com.apple.apple.models.entity.Cliente;

public interface IClienteDao {
    
    public List<Cliente> findAll();
}
