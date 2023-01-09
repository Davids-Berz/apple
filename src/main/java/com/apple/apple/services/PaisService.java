package com.apple.apple.services;

import java.util.List;

import com.apple.apple.models.entity.Pais;

public interface PaisService {
    
    public List<Pais> paises();
    public Pais getId(Integer id);
}
