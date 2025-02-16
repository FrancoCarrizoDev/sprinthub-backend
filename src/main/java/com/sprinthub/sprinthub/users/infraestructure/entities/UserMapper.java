package com.sprinthub.sprinthub.users.infraestructure.entities;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.domain.models.UserAuth;
import com.sprinthub.sprinthub.auth.infraestructure.entities.UserAuthEntity;
import com.sprinthub.sprinthub.auth.infraestructure.entities.UserAuthMapper;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.dtos.UserDto;
import com.sprinthub.sprinthub.users.domain.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    private final UserAuthMapper userAuthMapper;

    public UserMapper(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

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
        if (domain.getUserAuth() != null) {
            UserAuthEntity userAuthEntity = userAuthMapper.toEntity(domain.getUserAuth());
            // Además, para la relación bidireccional
            userAuthEntity.setUser(entity);
            entity.setUserAuth(userAuthEntity);
        }
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

        if(entity.getUserAuth() != null) {
            UserAuth userAuth = userAuthMapper.toDomain(entity.getUserAuth());
            userAuth.setUser(domain);
            domain.setUserAuth(userAuth);
        }
        return domain;
    }

    public User toDomain(CreateUserDto dto) {
        User domain = new User();
        domain.setFirstName(dto.getFirstName());
        domain.setLastName(dto.getLastName());
        domain.setEmail(dto.getEmail());
        domain.setActive(true);
        domain.setCreatedAt(LocalDateTime.now());
        domain.setUpdatedAt(LocalDateTime.now());

        UserAuth userAuth = new UserAuth();
        userAuth.setAuthProvider(AuthProviderEnum.CREDENTIALS);
        userAuth.setExternalId(null);
        userAuth.setVerified(false);
        userAuth.setLastLogin(null);
        userAuth.setPasswordHash(dto.getPassword());
        userAuth.setCreatedAt(LocalDateTime.now());
        userAuth.setVerificationCode(null);
        userAuth.setVerificationExpiresAt(null);
        domain.setUserAuth(userAuth);

        return domain;
    }


    public User fromOAuthUserDTO(OAuthUserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setActive(true);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(null);
        UserAuth userAuth = new UserAuth();
        userAuth.setExternalId(userDto.getExternalId());
        userAuth.setAuthProvider(AuthProviderEnum.GOOGLE);
        userAuth.setLastLogin(LocalDateTime.now());
        userAuth.setVerified(true);
        user.setUserAuth(userAuth);
        return user;
    }

    public UserDto toDto(User domain) {
        if (domain == null) {
            return null;
        }

        return UserDto.builder().id(domain.getId())
                .email(domain.getEmail())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .isActive(domain.isActive())
                .authProvider(domain.getUserAuth().getAuthProvider().name())
                .externalId(domain.getUserAuth().getExternalId())
                .isVerified(domain.getUserAuth().isVerified())
                .lastLogin(domain.getUserAuth().getLastLogin())
                .build();
    }
}
