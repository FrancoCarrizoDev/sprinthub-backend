package com.sprinthub.sprinthub.auth.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter googleJwtAuthenticationFilter; // Filtro existente para Google OAuth
    private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter; // Nuevo filtro para JWT personalizados

    public SecurityConfig(JwtAuthenticationFilter googleJwtAuthenticationFilter,
                          CustomJwtAuthenticationFilter customJwtAuthenticationFilter) {
        this.googleJwtAuthenticationFilter = googleJwtAuthenticationFilter;
        this.customJwtAuthenticationFilter = customJwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(googleJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customJwtAuthenticationFilter, JwtAuthenticationFilter.class); // El nuevo filtro se ejecuta después

        return http.build();
    }
}
