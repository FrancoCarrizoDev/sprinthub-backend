package com.sprinthub.sprinthub.auth.infraestructure.security;

import com.sprinthub.sprinthub.auth.domain.services.PasswordHashes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHashes {
    private final PasswordEncoder passwordEncoder;



    public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
