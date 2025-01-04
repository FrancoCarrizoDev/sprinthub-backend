package com.sprinthub.sprinthub.auth.domain.services;

public interface PasswordHashes {
    String hashPassword(String rawPassword);
}
