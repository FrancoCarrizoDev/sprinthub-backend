package com.sprinthub.sprinthub.auth.application.usecases;

import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

public class CheckEmailUseCase {

    private final UserRepository userRepository;

    public CheckEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email) {
        return userRepository.existsByEmail(email);
    }
}
