package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators;

import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.InvalidCredentialsException;
import com.sprinthub.sprinthub.users.domain.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordValidationRule implements CustomJwtAuthValidationRule {
    private final PasswordEncoder passwordEncoder;

    public PasswordValidationRule(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validate(User user, LoginRequestDto request) {
        if (!passwordEncoder.matches(request.getPassword(), user.getUserAuth().getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}