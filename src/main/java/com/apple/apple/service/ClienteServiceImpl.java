package com.apple.apple.service;

import com.apple.apple.models.dao.IClienteCrud;
import com.apple.apple.models.dao.IClienteDao;
import com.apple.apple.models.dao.IProductoDao;
import com.apple.apple.models.entity.Cliente;
import com.apple.apple.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("ClienteServiceCrudRepository")
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IClienteCrud clienteCrud;

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteCrud.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteCrud.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteCrud.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteCrud.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteCrud.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByName(String term) {
        return productoDao.findByName(term);
    }
}
