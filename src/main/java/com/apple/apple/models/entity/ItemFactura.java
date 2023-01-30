package com.apple.apple.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "item_factura")
public class ItemFactura implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long calcularImporte() {
        return cantidad.longValue();
    }
}
