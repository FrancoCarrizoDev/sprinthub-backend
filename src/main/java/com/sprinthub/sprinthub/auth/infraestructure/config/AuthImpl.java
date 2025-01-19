package com.sprinthub.sprinthub.auth.infraestructure.config;

import com.sprinthub.sprinthub.auth.domain.models.Auth;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class AuthImpl implements Auth {
    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusMinutes(10);
    }
}
