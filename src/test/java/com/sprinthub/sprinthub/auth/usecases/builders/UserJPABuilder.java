package com.sprinthub.sprinthub.auth.usecases.builders;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.domain.models.UserAuthJPA;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

import java.util.UUID;

public class UserJPABuilder {

    private final UserEntity user;

    public UserJPABuilder() {
        user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setCreatedAt(null);
        user.setEmail("test@gmail.com");
        user.setFirstName("Test");
        user.setLastName("Test");

        UserAuthJPA auth = new UserAuthJPA();
        auth.setAuthProvider(AuthProviderEnum.CREDENTIALS);
        auth.setUser(user);
        auth.setPasswordHash("password");
        auth.setCreatedAt(null);
        auth.setId(UUID.randomUUID());
        auth.setExternalId(null);
        auth.setLastLogin(null);
        auth.setVerificationCode(null);

        user.setAuth(auth);
    }

    public UserJPABuilder withAuthProvider(AuthProviderEnum authProviderEnum) {
        user.getAuth().setAuthProvider(authProviderEnum);
        return this;
    }

    public UserJPABuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserEntity build() {
        return user;
    }
}