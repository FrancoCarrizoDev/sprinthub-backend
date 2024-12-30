package com.sprinthub.sprinthub.auth.application.usecases;

import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestResponseDto;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.shared.exceptions.InvalidCredentialsException;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

public class ValidateLoginUseCase {

    private final UserRepository userRepository;

    private final CustomJwtAuthenticationProvider customJwtAuthenticationProvider;

    private final PasswordEncoder passwordEncoder;

    public ValidateLoginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomJwtAuthenticationProvider customJwtAuthenticationProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customJwtAuthenticationProvider = customJwtAuthenticationProvider;
    }

    public LoginRequestResponseDto execute(LoginRequestDto request) {

        Optional<UserJPA> user = userRepository.findByEmail(request.getEmail());

        if(user.isEmpty()) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UserJPA userJPA = user.get();

        if(Objects.equals(userJPA.getAuth().getAuthProvider(), "GOOGLE")) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if(!passwordEncoder.matches(request.getPassword(), userJPA.getAuth().getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }


        return LoginRequestResponseDto.builder()
                .email(userJPA.getEmail())
                .externalId(userJPA.getAuth().getExternalId())
                .token(customJwtAuthenticationProvider.generateToken(userJPA.getEmail()))
                .firstName(userJPA.getFirstName())
                .lastName(userJPA.getLastName())
                .build();



    }
}
