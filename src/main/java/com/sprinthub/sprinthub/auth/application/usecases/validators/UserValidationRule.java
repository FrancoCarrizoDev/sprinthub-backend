package com.sprinthub.sprinthub.auth.application.usecases.validators;

import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public interface UserValidationRule {
    void validate(UserEntity user);
}