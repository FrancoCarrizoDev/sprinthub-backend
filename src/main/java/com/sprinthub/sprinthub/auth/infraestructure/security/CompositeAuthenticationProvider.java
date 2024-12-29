package com.sprinthub.sprinthub.auth.infraestructure.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeAuthenticationProvider implements AuthenticationProvider {

    private final List<AuthenticationProvider> providers;

    public CompositeAuthenticationProvider(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())) {
                try {
                    return provider.authenticate(authentication); // Si uno autentica, retorna
                } catch (AuthenticationException e) {
                    // Continuar con el siguiente proveedor si falla
                }
            }
        }
        throw new BadCredentialsException("Authentication failed for all providers");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true; // Soporta cualquier tipo de autenticación
    }
}