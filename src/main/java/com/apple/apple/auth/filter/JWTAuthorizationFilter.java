package com.apple.apple.auth.filter;

import com.apple.apple.auth.SimpleGrantedAuthoritiesMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (!requieresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }
        boolean validToken;
        Claims token = null;
        try {
            token = Jwts.parser()
                    .setSigningKey(JWTAuthenticationFilter.KEY.getBytes())
                    .parseClaimsJws(header.replace("Bearer ", ""))
                    .getBody();
            validToken = true;
        } catch (JwtException | IllegalArgumentException e) {
            validToken = false;
        }

        UsernamePasswordAuthenticationToken authenticationToken = null;

        if (validToken) {
            String username = token.getSubject();
            Object roles = token.get("authorities");
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.asList
                            (new ObjectMapper()
                                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixin.class)
                                    .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
            authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

        } else {

        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    protected boolean requieresAuthentication(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }
}
