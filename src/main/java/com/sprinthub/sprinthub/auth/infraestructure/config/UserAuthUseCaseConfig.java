package com.sprinthub.sprinthub.auth.infraestructure.config;

import com.sprinthub.sprinthub.auth.application.usecases.CheckEmailUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthUseCaseConfig {


    @Bean
    public CheckEmailUseCase checkEmailUseCase(UserRepository userRepository) {
        return new CheckEmailUseCase(userRepository);
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
