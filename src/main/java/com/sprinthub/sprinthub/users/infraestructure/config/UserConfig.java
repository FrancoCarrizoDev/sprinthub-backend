package com.sprinthub.sprinthub.users.infraestructure.config;


import com.sprinthub.sprinthub.messaging.adapters.out.KafkaProducerAdapter;
import com.sprinthub.sprinthub.users.application.services.UserEventPublisher;
import com.sprinthub.sprinthub.auth.domain.models.Auth;
import com.sprinthub.sprinthub.auth.domain.services.PasswordHashes;
import com.sprinthub.sprinthub.users.application.usecases.CreateNewUserUseCase;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateNewUserUseCase createNewUserUseCase(UserRepository userRepository,
                                                     UserMapper userMapper,
                                                     PasswordHashes passwordHasher,
                                                      UserEventPublisher userEventPublisher,
                                                     Auth auth
                                                     ) {
        return new CreateNewUserUseCase(userRepository, userMapper, passwordHasher, userEventPublisher, auth);
    }

    @Bean
    public UserEventPublisher userEventPublisher(KafkaProducerAdapter kafkaProducerAdapter) {
        return new UserEventPublisher(kafkaProducerAdapter);
    }
}