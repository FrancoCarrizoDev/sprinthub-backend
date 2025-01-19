package com.sprinthub.sprinthub.auth.infraestructure.config;


import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.CustomJwtAuthenticationUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators.CustomJwtAuthValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators.PasswordValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.OAuthJwtAuthenticationUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators.OAuthJwtAuthValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators.RejectIfUserHasCredentialsAuthRule;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.VerificationCodeUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators.RejectIfUserHasGoogleAuthRule;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators.VerificationCodeMatchesValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators.VerificationCodeValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.validators.*;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserAuthConfig {


    @Bean
    public RejectIfUserHasCredentialsAuthRule rejectIfUserHasCredentialsAuthRule() {
        return new RejectIfUserHasCredentialsAuthRule();
    }

    @Bean
    public ActiveUserValidationRule activeUserValidationRule() {
        return new ActiveUserValidationRule();
    }

    @Bean
    public UserVerifiedValidationRule verifiedUserValidationRule() {
        return new UserVerifiedValidationRule();
    }


    @Bean
    public PasswordValidationRule passwordMatchesValidationRule(PasswordEncoder passwordEncoder) {
        return new PasswordValidationRule(passwordEncoder);
    }

    @Bean
    public VerificationCodeMatchesValidationRule verificationCodeMatchesValidationRule() {
        return new VerificationCodeMatchesValidationRule();
    }

    @Bean
    public RejectIfUserHasGoogleAuthRule rejectIfUserHasGoogleAuthRule() {
        return new RejectIfUserHasGoogleAuthRule();
    }

    @Bean
    public List<VerificationCodeValidationRule> verificationCodeValidationRules(
            VerificationCodeMatchesValidationRule verificationCodeMatchesValidationRule,
            RejectIfUserHasGoogleAuthRule rejectIfUserHasGoogleAuthRule
    ) {
        return List.of(
                rejectIfUserHasGoogleAuthRule,
                verificationCodeMatchesValidationRule

        );
    }


    @Bean
    public List<UserValidationRule> userValidationRules(
            ActiveUserValidationRule activeUserValidationRule,
            UserVerifiedValidationRule userVerifiedValidationRule
    ) {
        return List.of(
                activeUserValidationRule,
                userVerifiedValidationRule
        );
    }

    @Bean
    public List<OAuthJwtAuthValidationRule> oauthJwtAuthValidationRules(
            RejectIfUserHasCredentialsAuthRule rejectIfUserHasCredentialsAuthRule

    ) {
        return List.of(
                rejectIfUserHasCredentialsAuthRule
        );
    }





    @Bean
    public List<CustomJwtAuthValidationRule> loginRequestValidationRules(
            PasswordValidationRule passwordMatchesValidationRule
    ) {
        return List.of(
                passwordMatchesValidationRule
        );
    }


    @Bean
    public CustomJwtAuthenticationUseCase customJwtAuthenticationUseCase(
            UserRepository userRepository,
            CustomJwtAuthenticationProvider customJwtAuthenticationProvider,
            List<UserValidationRule> userValidationRules,
            List<CustomJwtAuthValidationRule> loginRequestValidationRules
    ) {
        return new CustomJwtAuthenticationUseCase(
                userRepository,
                customJwtAuthenticationProvider,
                userValidationRules,
                loginRequestValidationRules
        );
    }


    @Bean
    public OAuthJwtAuthenticationUseCase oAuthJwtAuthenticationUseCase(
            UserRepository userRepository,
            List<UserValidationRule> userValidationRules,
            List<OAuthJwtAuthValidationRule> oauthJwtAuthValidationRules,
            UserMapper userMapper
    ) {
        return new OAuthJwtAuthenticationUseCase(
                userRepository,
                userValidationRules,
                oauthJwtAuthValidationRules,
                userMapper
        );
    }

    @Bean
    public VerificationCodeUseCase verifyVerificationCodeUseCase(
            UserRepository userRepository,
            List<VerificationCodeValidationRule> verificationCodeValidationRules,
            UserMapper userMapper
    ) {
        return new VerificationCodeUseCase(
                userRepository,
                verificationCodeValidationRules,
                userMapper
        );
    }
}