package com.apple.apple.auth.filter;

import com.apple.apple.models.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if (username != null && password != null) {
            logger.info("Username desde request parameter (form-data): " + username);
            logger.info("Username desde request parameter (form-data): " + password);
        } else {
            Usuario user = null;
            try {
                user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
                username = user.getUsername();
                password = user.getPassword();
                logger.info("Username desde request (raw): " + username);
                logger.info("Username desde request (raw): " + password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);



        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writer().writeValueAsString(roles));

        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512,"w$5mZDfEJv@9gk#2GcQx8yLHnN^&p!KuP4tVlS7z@h3rbYXaT".getBytes())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000L))
                .compact();
        response.addHeader("Authorization", "Bearer "+token);
        Map<String, Object> body = new HashMap<>();

        body.put("token", token);
        body.put("user", ((User) authResult.getPrincipal()));
        body.put("mensaje", String.format("Hola %s, has iniciado sesion con exito", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application.json");

    }
}
