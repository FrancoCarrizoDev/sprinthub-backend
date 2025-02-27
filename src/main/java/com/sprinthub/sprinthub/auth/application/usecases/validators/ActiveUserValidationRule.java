package com.sprinthub.sprinthub.auth.application.usecases.validators;


import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserInactiveException;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;


public class ActiveUserValidationRule implements UserValidationRule {
    @Override
    public void validate(User user) {
        if (!user.isActive()) {
            throw new UserInactiveException("User is inactive");
        }
    }
}