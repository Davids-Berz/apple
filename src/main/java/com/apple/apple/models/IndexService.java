package com.apple.apple.models;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("IndexService")
public class IndexService implements IService{
    
    public String operacion() {
        return "ejecutando IndexService";
    }

    @Override
    public String usuario() {
        return "David Saldivar Berzosa";
    }
}
