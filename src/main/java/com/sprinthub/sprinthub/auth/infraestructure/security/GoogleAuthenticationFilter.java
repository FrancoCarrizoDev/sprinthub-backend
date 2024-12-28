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
public class GoogleAuthenticationFilter extends OncePerRequestFilter {

    private final GoogleAuthenticationProvider googleAuthenticationProvider;

    public GoogleAuthenticationFilter(GoogleAuthenticationProvider googleAuthenticationProvider) {
        this.googleAuthenticationProvider = googleAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // Saltar si el endpoint es público
        if (path.equals("/api/auth/login") || path.equals("/api/users/create")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validar Google OAuth token solo para /api/auth/signin o endpoints privados
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // Crear un objeto Authentication con el token como credencial
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, token);

                // Delegar al proveedor de autenticación
                Authentication authResult = googleAuthenticationProvider.authenticate(authentication);

                // Establecer el contexto de seguridad si la autenticación es exitosa
                SecurityContextHolder.getContext().setAuthentication(authResult);
                filterChain.doFilter(request, response);
                return;
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Google ID token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}