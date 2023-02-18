package com.apple.apple.service;

import com.apple.apple.models.dao.IClienteDao;
import com.apple.apple.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }
}
