package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators;

import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public interface CustomJwtAuthValidationRule {
    void validate(UserEntity user, LoginRequestDto request);

}
