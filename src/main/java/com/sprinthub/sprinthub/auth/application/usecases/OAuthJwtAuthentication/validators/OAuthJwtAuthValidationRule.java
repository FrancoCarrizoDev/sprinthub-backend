package com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators;

import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public interface OAuthJwtAuthValidationRule {
    void validate(User user, OAuthUserDto request);
}
