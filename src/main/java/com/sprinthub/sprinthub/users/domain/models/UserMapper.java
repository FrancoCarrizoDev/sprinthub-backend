package com.sprinthub.sprinthub.users.domain.models;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.domain.models.UserAuthJPA;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.dtos.UserDto;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setIsActive(domain.isActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User domain = new User();
        domain.setId(entity.getId());
        domain.setEmail(entity.getEmail());
        domain.setFirstName(entity.getFirstName());
        domain.setLastName(entity.getLastName());
        domain.setActive(entity.getIsActive());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public UserDto toDTO(UserEntity user) {
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

    public UserEntity fromDTO(UserDto dto) {
        UserEntity user = new UserEntity();
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

    public UserEntity fromCreateDTO(CreateUserDto dto) {
        UserEntity user = new UserEntity();
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
