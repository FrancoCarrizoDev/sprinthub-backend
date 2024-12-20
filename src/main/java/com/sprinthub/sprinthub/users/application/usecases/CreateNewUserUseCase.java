package com.sprinthub.sprinthub.users.application.usecases;

import com.sprinthub.sprinthub.auth.domain.services.PasswordHasher;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.mappers.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateNewUserUseCase {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private final PasswordHasher passwordHasher;

    private final Logger logger = LoggerFactory.getLogger(CreateNewUserUseCase.class);

    public CreateNewUserUseCase(UserRepository userRepository, UserMapper userMapper, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordHasher = passwordHasher;
    }

    public void execute(CreateUserDto createUserDto) {
        try {
            if (userRepository.existsByEmail(createUserDto.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            createUserDto.setPassword(passwordHasher.hashPassword(createUserDto.getPassword()));
            userRepository.save(userMapper.fromCreateDTO(createUserDto));
        }catch (Exception e){
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user");
        }


    }
}
