package com.sprinthub.sprinthub.users.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinthub.sprinthub.auth.domain.models.Auth;
import com.sprinthub.sprinthub.auth.domain.services.PasswordHashes;
import com.sprinthub.sprinthub.messaging.domain.models.UserCreatedEvent;
import com.sprinthub.sprinthub.messaging.adapters.out.KafkaProducerAdapter;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.dtos.UserDto;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CreateNewUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordHashes passwordHasher;
    private final KafkaProducerAdapter kafkaProducerAdapter;
    private final Logger logger = LoggerFactory.getLogger(CreateNewUserUseCase.class);
    private final Auth auth;


    public CreateNewUserUseCase(UserRepository userRepository, UserMapper userMapper, PasswordHashes passwordHasher, KafkaProducerAdapter kafkaProducerAdapter, Auth auth) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordHasher = passwordHasher;
        this.kafkaProducerAdapter = kafkaProducerAdapter;
        this.auth = auth;
    }


    public UserDto execute(CreateUserDto createUserDto) {
        try {
            if (userRepository.existsByEmail(createUserDto.getEmail())) {
                throw new IllegalArgumentException(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
            }

            createUserDto.setPassword(passwordHasher.hashPassword(createUserDto.getPassword()));
            createUserDto.setVerificationCode(auth.generateVerificationCode());
            createUserDto.setVerificationExpiresAt(auth.calculateExpirationTime());

            User userSaved = userRepository.save(userMapper.toDomain(createUserDto));
            UserCreatedEvent event = new UserCreatedEvent(userSaved.getId(), userSaved.getEmail(), userSaved.getUserAuth().getVerificationCode());
            kafkaProducerAdapter.send("user.created", event.getUserId().toString(), event);

            return userMapper.toDto(userSaved);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user");
        }
    }



}
