package com.sprinthub.sprinthub.auth.application.usecases;

import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.domain.models.UserAuthJPA;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

import java.time.LocalDateTime;

public class SaveOauthUserUseCase {

    private final UserRepository userRepository;

    public SaveOauthUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(OAuthUserDto user) {
        UserJPA userJPA =  new UserJPA();
        userJPA.setEmail(user.getEmail());
        userJPA.setFirstName(user.getFirstName());
        userJPA.setLastName(null);
        UserAuthJPA userAuth = new UserAuthJPA();
        userAuth.setExternalId(user.getExternalId());
        userAuth.setAuthProvider("google");
        userAuth.setUser(userJPA);
        userAuth.setLastLogin(LocalDateTime.now());
        userAuth.setVerified(true);
        userJPA.setAuth(userAuth);

        try {
            userRepository.save(userJPA);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user");
        }
    }
}
