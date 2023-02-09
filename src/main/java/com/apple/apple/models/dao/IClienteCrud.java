package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClienteCrud extends JpaRepository<Cliente,Long> {

    @Query("select c from Cliente c join fetch c.facturas f where c.id = ?1")
    public Cliente fetchByIdWithFacturas(Long id);
}
