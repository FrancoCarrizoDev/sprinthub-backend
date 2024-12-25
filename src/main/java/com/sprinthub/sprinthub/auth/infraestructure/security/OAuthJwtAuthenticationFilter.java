package com.sprinthub.sprinthub.auth.infraestructure.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;


@Component
public class OAuthJwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String CLIENT_ID = "890001511003-13onja4lfbciddspiim8ulsaet20ut3i.apps.googleusercontent.com";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null) {
            try {
                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                        new NetHttpTransport(), GsonFactory.getDefaultInstance())
                        .setAudience(Collections.singletonList(CLIENT_ID))
                        .build();

                GoogleIdToken idToken = verifier.verify(token);

                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    String email = payload.getEmail();

                    System.out.println("Token válido. Email extraído: " + email);

                    // Crear y establecer el objeto de autenticación
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("Autenticación configurada: " + SecurityContextHolder.getContext().getAuthentication());
                } else {
                    System.out.println("Token inválido o no se pudo verificar.");
                    SecurityContextHolder.clearContext();
                }
            } catch (Exception e) {
                System.out.println("Error al procesar el token: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("No se encontró un token en la solicitud.");
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + bearerToken); // Agrega este log
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}