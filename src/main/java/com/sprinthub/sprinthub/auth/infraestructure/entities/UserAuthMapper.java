package com.sprinthub.sprinthub.auth.infraestructure.entities;

import com.sprinthub.sprinthub.auth.domain.models.UserAuth;
import com.sprinthub.sprinthub.users.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper {


    public UserAuth toDomain(UserAuthEntity entity) {
        if (entity == null) {
            return null;
        }



        return UserAuth.builder()
                .id(entity.getId())
                .authProvider(entity.getAuthProvider())
                .passwordHash(entity.getPasswordHash())
                .externalId(entity.getExternalId())
                .verificationCode(entity.getVerificationCode())
                .verificationExpiresAt(entity.getVerificationExpiresAt())
                .isVerified(entity.isVerified())
                .lastLogin(entity.getLastLogin())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }

    public UserAuthEntity toEntity(UserAuth domain) {
        if (domain == null) {
            return null;
        }

        UserAuthEntity entity = new UserAuthEntity();
        entity.setId(domain.getId());
        entity.setAuthProvider(domain.getAuthProvider());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setExternalId(domain.getExternalId());
        entity.setVerificationCode(domain.getVerificationCode());
        entity.setVerificationExpiresAt(domain.getVerificationExpiresAt());
        entity.setVerified(domain.isVerified());
        entity.setLastLogin(domain.getLastLogin());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());


        return entity;
    }
}
