package com.sprinthub.sprinthub.auth.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomJwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtAuthenticationFilter(CustomJwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // Saltar si el endpoint es público o exclusivo de Google OAuth
        if (path.equals("/api/auth/login") || path.equals("/api/users/create") || path.equals("/api/auth/signin")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validar JWT custom
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Authentication authentication = jwtAuthenticationProvider.authenticate(
                        new UsernamePasswordAuthenticationToken(token, null));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                // Si falla, no autenticamos, pero dejamos que otro filtro intente
            }
        }

        filterChain.doFilter(request, response);
    }
}