package com.sprinthub.sprinthub.auth.domain.models;

import java.time.LocalDateTime;
import java.util.Random;

public class Auth {

    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusMinutes(10);
    }
}