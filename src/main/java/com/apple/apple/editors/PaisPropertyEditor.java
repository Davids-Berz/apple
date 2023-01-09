package com.apple.apple.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apple.apple.services.PaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private PaisService service;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (id != null && id.length() > 0) {
            try {
                setValue(service.getId(Integer.valueOf(id)));
            } catch (Exception e) {
                setValue(null);
            }
        } else {
            setValue(null);
        }
    }

}
