package com.apple.apple.models;

import org.springframework.stereotype.Service;

@Service
public class IndexService implements IService{
    
    public String operacion() {
        return "ejecutando IndexService";
    }
}
