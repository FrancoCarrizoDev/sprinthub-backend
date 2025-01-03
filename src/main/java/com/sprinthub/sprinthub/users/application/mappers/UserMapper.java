package com.sprinthub.sprinthub.users.application.mappers;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.domain.models.UserAuthJPA;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.dtos.UserDto;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class UserMapper {


    public UserDto toDTO(UserJPA user) {
        UserAuthJPA userAuth = user.getAuth();
        return UserDto.builder().id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .authProvider(userAuth.getAuthProvider().toString())
                .externalId(userAuth.getExternalId())
                .isVerified(userAuth.isVerified())
                .lastLogin(userAuth.getLastLogin())
                .build();
    }

    public UserJPA fromDTO(UserDto dto) {
        UserJPA user = new UserJPA();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setIsActive(dto.getIsActive());
        UserAuthJPA auth = new UserAuthJPA();
        auth.setAuthProvider(AuthProviderEnum.fromString(dto.getAuthProvider()));
        auth.setExternalId(dto.getExternalId());
        auth.setVerified(dto.isVerified());
        auth.setLastLogin(dto.getLastLogin());
        user.setAuth(auth);
        return user;
    }

    public UserJPA fromCreateDTO(CreateUserDto dto) {
        UserJPA user = new UserJPA();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setIsActive(true);
        UserAuthJPA auth = new UserAuthJPA();
        auth.setAuthProvider(AuthProviderEnum.CREDENTIALS);
        auth.setExternalId(null);
        auth.setVerified(false);
        auth.setLastLogin(LocalDateTime.now());
        auth.setPasswordHash(dto.getPassword());
        user.setAuth(auth);
        auth.setUser(user);
        return user;
    }
}
