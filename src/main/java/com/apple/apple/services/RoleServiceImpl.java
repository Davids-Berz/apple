package com.apple.apple.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apple.apple.models.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {

    private List<Role> roles;

    public RoleServiceImpl() {
        this.roles = new ArrayList<Role>();
        this.roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
        this.roles.add(new Role(1, "Usuario", "ROLE_USUARIO"));
        this.roles.add(new Role(1, "Moderador", "ROLE_MODERATOR"));
    }

    @Override
    public Role getId(Integer id) {
        Role role = roles.stream()
        .filter(input -> input.getId() == id)
        .findFirst().orElse(null);
        return role;
    }

    @Override
    public List<Role> roles() {
        return roles;
    }
    
    
}
