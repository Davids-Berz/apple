package com.apple.apple.service;

import com.apple.apple.models.dao.IUsuarioCrud;
import com.apple.apple.models.entity.Role;
import com.apple.apple.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioCrud usuarioCrud;

    private static final Logger LOG = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioCrud.findByUsername(username);

        if (usuario == null) {
            LOG.error("No existe el usuario " + username);
            throw new UsernameNotFoundException("Usuario no existe");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role: usuario.getRoles()) {
            LOG.info("Role: " + role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorities == null) {
            LOG.error("Error: Usuario sin roles");
            throw new UsernameNotFoundException("Usuario sin roles asignados");
        }

        return new User(username, usuario.getPassword(), usuario.getEnable(), true, true, true, authorities);
    }
}
