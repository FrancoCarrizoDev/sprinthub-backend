package com.sprinthub.sprinthub.users.application.usecases;

import com.sprinthub.sprinthub.users.application.services.UserEventPublisher;
import com.sprinthub.sprinthub.auth.domain.models.Auth;
import com.sprinthub.sprinthub.auth.domain.services.PasswordHashes;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.dtos.UserDto;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CreateNewUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordHashes passwordHasher;
    private final UserEventPublisher userEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(CreateNewUserUseCase.class);
    private final Auth auth;


    public CreateNewUserUseCase(UserRepository userRepository, UserMapper userMapper, PasswordHashes passwordHasher, UserEventPublisher userEventPublisher, Auth auth) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordHasher = passwordHasher;
        this.userEventPublisher = userEventPublisher;
        this.auth = auth;
    }

    public UserDto execute(CreateUserDto createUserDto) {
        try {
            if (userRepository.existsByEmail(createUserDto.getEmail())) {
                throw new IllegalArgumentException(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
            }

            createUserDto.setPassword(passwordHasher.hashPassword(createUserDto.getPassword()));
            User userSaved = (userMapper.toDomain(createUserDto));
            userSaved.getUserAuth().setVerificationCode(auth.generateVerificationCode());
            userSaved.getUserAuth().setVerificationExpiresAt(auth.calculateExpirationTime());
            userEventPublisher.publishUserCreatedEvent(userRepository.save(userSaved));

            return userMapper.toDto(userSaved);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user");
        }
    }

}
