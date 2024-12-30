package com.sprinthub.sprinthub.auth.infraestructure.config;

import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.ValidateLoginUseCase;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserAuthUseCaseConfig {


    @Bean
    ValidateLoginUseCase validateLoginUseCase(UserRepository userRepository, CustomJwtAuthenticationProvider customJwtAuthenticationProvider, PasswordEncoder passwordEncoder) {
        return new ValidateLoginUseCase(userRepository, passwordEncoder, customJwtAuthenticationProvider);
    }

    @Bean
    public CheckOauthUserStatusUseCase checkOauthUserStatusUseCase(UserRepository userRepository) {
        return new CheckOauthUserStatusUseCase(userRepository);
    }

    @Bean
    public SaveOauthUserUseCase saveOauthUserUseCase(UserRepository userRepository) {
        return new SaveOauthUserUseCase(userRepository);
    }

}
