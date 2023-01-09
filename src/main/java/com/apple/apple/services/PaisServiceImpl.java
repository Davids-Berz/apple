package com.apple.apple.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.apple.apple.models.entity.Pais;

@Service
public class PaisServiceImpl implements PaisService{

    private List<Pais> paises = Arrays.asList(
        new Pais(1, "AR","Argentina"), 
        new Pais(2, "BO","Bolivia"),
        new Pais(3, "CL","Chile"),
        new Pais(4, "CO","Colombia"),
        new Pais(5, "EC","Ecuador"),
        new Pais(6, "GT","Guatemala"),
        new Pais(7, "HN","Honduras"),
        new Pais(8, "MX","Mexico"),
        new Pais(9, "NI","Nicaragua"),
        new Pais(10, "PA","Panamá"),
        new Pais(11, "PE","Perú"),
        new Pais(12, "PR","Puerto Rico"));

    @Override
    public List<Pais> paises() {
       return paises;
    }

    @Override
    public Pais getId(Integer id) {
        Pais pais = paises.stream()
        .filter(input -> input.getId() == id)
        .findFirst().orElse(null);
        return pais;
    }
    
}
