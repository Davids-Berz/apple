package com.apple.apple.models.dao;

import com.apple.apple.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaDao extends JpaRepository<Factura, Long> {

}
