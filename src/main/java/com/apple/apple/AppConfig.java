package com.apple.apple;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apple.apple.models.domain.ItemFactura;
import com.apple.apple.models.domain.Producto;

@Configuration
public class AppConfig {
    
    @Bean
    public List<ItemFactura> inyectarItems() {
        Producto producto1 = new Producto("Camara Sony", 100);
        Producto producto2 = new Producto("Bicicleta", 200);

        ItemFactura linea1 = new ItemFactura(producto1, 2);
        ItemFactura linea2 = new ItemFactura(producto2, 4);

        return Arrays.asList(linea1, linea2);
    }
}
