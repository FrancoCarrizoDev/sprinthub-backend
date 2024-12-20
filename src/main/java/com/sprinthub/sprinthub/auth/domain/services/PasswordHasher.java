package com.sprinthub.sprinthub.auth.domain.services;

public interface PasswordHasher {
    String hashPassword(String rawPassword);
}
