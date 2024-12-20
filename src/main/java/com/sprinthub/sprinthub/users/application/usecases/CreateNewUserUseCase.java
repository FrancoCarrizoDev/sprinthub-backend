package com.sprinthub.sprinthub.users.application.usecases;

import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.mappers.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

public class CreateNewUserUseCase {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public CreateNewUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void execute(CreateUserDto createUserDto) {
        userRepository.save(userMapper.fromCreateDTO(createUserDto));
    }
}
