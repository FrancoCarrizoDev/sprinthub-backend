package com.sprinthub.sprinthub.auth.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private final OAuthJwtAuthenticationFilter googleJwtAuthenticationFilter; // Filtro existente para Google OAuth
// Nuevo filtro para JWT personalizados

    public SecurityConfig(OAuthJwtAuthenticationFilter googleJwtAuthenticationFilter
                          ) {
        this.googleJwtAuthenticationFilter = googleJwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/users/create").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(googleJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // El filtro de Google se ejecuta primero
                ; // El nuevo filtro se ejecuta después

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
