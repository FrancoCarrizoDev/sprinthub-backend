package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication;

import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestResponseDto;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.InvalidCredentialsException;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators.CustomJwtAuthValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.validators.UserValidationRule;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

import java.util.List;

public class CustomJwtAuthenticationUseCase {

    private final UserRepository userRepository;
    private final CustomJwtAuthenticationProvider customJwtAuthenticationProvider;

    private final List<UserValidationRule> userValidationRules;
    private final List<CustomJwtAuthValidationRule> customJwtAuthValidationRules;



    public CustomJwtAuthenticationUseCase(UserRepository userRepository, CustomJwtAuthenticationProvider customJwtAuthenticationProvider, List<UserValidationRule> userValidationRules, List<CustomJwtAuthValidationRule> loginRequestValidationRules) {
        this.userRepository = userRepository;
        this.customJwtAuthenticationProvider = customJwtAuthenticationProvider;
        this.userValidationRules = userValidationRules;
        this.customJwtAuthValidationRules = loginRequestValidationRules;
    }


    public LoginRequestResponseDto execute(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new InvalidCredentialsException(ExceptionMessages.EMAIL_NOT_PROVIDED)
        );

        for (CustomJwtAuthValidationRule rule : customJwtAuthValidationRules) {
            rule.validate(user, request);
        }

        for (UserValidationRule rule : userValidationRules) {
            rule.validate(user);
        }


        return LoginRequestResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .externalId(user.getUserAuth().getExternalId())
                .token(customJwtAuthenticationProvider.generateToken(user.getEmail()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }


}
