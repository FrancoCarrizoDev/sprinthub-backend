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

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        String strategy = request.getHeader("X-Auth-Strategy"); // Leer el encabezado

        if (strategy == null || (!"google".equalsIgnoreCase(strategy) && !"custom".equalsIgnoreCase(strategy))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing X-Auth-Strategy header");
            return;
        }

        if (token != null && strategy != null) {
            try {
                Authentication authentication = null;

                // Decidir la estrategia según el encabezado
                if ("google".equalsIgnoreCase(strategy)) {
                    authentication = new UsernamePasswordAuthenticationToken(null, token);
                } else if ("custom".equalsIgnoreCase(strategy)) {
                    authentication = new UsernamePasswordAuthenticationToken(null, token);
                }

                if (authentication != null) {
                    // Autenticar con el AuthenticationManager
                    Authentication authenticated = authenticationManager.authenticate(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authenticated);
                } else {
                    throw new BadCredentialsException("Invalid authentication strategy");
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

