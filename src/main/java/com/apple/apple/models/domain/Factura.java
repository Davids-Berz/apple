package com.apple.apple.models.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@RequestScope
public class Factura {

    @Value("${factura.descripcion}")
    private String descripcion;

    @Autowired
    private Cliente cliente;

    @Autowired
    private List<ItemFactura> items;

    @PostConstruct
    public void inicializar() {
        cliente.setNombre(cliente.getNombre() + " " + "Alfredo");
    }

    @PreDestroy
    public void destruir() {
        System.out.println("Se Destruye " + descripcion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }
}
