package com.apple.apple.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apple.apple.services.RoleService;

@Component
public class RolesEditor extends PropertyEditorSupport {

    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (id != null && id.length() > 0) {
            try {
                setValue(roleService.getId(Integer.valueOf(id)));
            } catch (NumberFormatException e) {
                setValue(null);
            }
        } else {
            setValue(null);
        }
    }

}
