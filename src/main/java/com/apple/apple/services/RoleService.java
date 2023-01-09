package com.apple.apple.services;

import java.util.List;

import com.apple.apple.models.entity.Role;

public interface RoleService {
    
    public List<Role> roles();
    public Role getId(Integer id);
}
