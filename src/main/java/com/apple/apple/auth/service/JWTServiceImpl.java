package com.apple.apple.auth.service;

import com.apple.apple.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService {

    public static final byte[] KEY = "w$5mZDfEJv@9gk#2GcQx8yLHnN^&p!KuP4tVlS7z@h3rbYXaT".getBytes();
    public static final Date EXPIRATION_DATE = new Date(System.currentTimeMillis() + 3600000);
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "authorization";

    @Override
    public String create(Authentication auth) throws IOException {
        String username = ((User) auth.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writer().writeValueAsString(roles));

        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .setIssuedAt(new Date())
                .setExpiration(EXPIRATION_DATE)
                .compact();
        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(resolve(token))
                .getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        Collection<? extends GrantedAuthority> authorities =
                Arrays.asList
                        (new ObjectMapper()
                                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
