package com.sprinthub.sprinthub.auth.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UnifiedAuthenticationFilter extends OncePerRequestFilter {

    private final CompositeAuthenticationProvider compositeAuthenticationProvider;

    public UnifiedAuthenticationFilter(CompositeAuthenticationProvider compositeAuthenticationProvider) {
        this.compositeAuthenticationProvider = compositeAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/api/auth/login") || path.equals("/api/users/create")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extraer el token sin "Bearer "

            try {
                // Crear el objeto de autenticación con el token como credencial
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, token);

                // Intentar autenticar usando el CompositeAuthenticationProvider
                Authentication authResult = compositeAuthenticationProvider.authenticate(authentication);

                // Establecer el contexto de seguridad si la autenticación es exitosa
                SecurityContextHolder.getContext().setAuthentication(authResult);
                filterChain.doFilter(request, response);
                return;
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed");
                return;
            }
        }

        // Continuar si no hay encabezado de autorización
        filterChain.doFilter(request, response);
    }
}