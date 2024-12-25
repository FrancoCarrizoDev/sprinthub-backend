package com.sprinthub.sprinthub.auth.infraestructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CombinedAuthenticationFilter extends OncePerRequestFilter {

    private static final String CLIENT_ID = "890001511003-13onja4lfbciddspiim8ulsaet20ut3i.apps.googleusercontent.com";
    private static final String SECRET_KEY = "tuClaveSecretaSuperSegura";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

       if (token != null) {
            if (isGoogleToken(token)) {
                handleGoogleToken(token);
            } else {
                handleCustomToken(token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleGoogleToken(String token) {
        // Lógica para manejar tokens de Google
    }

    private void handleCustomToken(String token) {
        // Lógica para manejar tokens personalizados
    }

    private boolean isGoogleToken(String token) {
        // Decodifica y verifica el issuer
        try {
            Claims claims = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return "https://accounts.google.com".equals(claims.getIssuer());
        } catch (Exception e) {
            return false;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}