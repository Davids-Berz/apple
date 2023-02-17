package com.apple.apple.auth.filter;

import com.apple.apple.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (!requieresAuthentication(token)) {
            chain.doFilter(request, response);
            return;
        }


        UsernamePasswordAuthenticationToken authenticationToken = null;

        if (jwtService.validate(token)) {
            String username = jwtService.getUsername(token);
            authenticationToken = new UsernamePasswordAuthenticationToken(username, null, jwtService.getRoles(token));
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
