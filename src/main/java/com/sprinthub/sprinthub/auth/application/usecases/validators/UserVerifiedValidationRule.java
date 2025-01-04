package com.sprinthub.sprinthub.auth.application.usecases.validators;


import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserNotVerifiedException;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public class UserVerifiedValidationRule implements UserValidationRule {
    @Override
    public void validate(UserEntity user) {
        if (!user.getAuth().isVerified()) {
            throw new UserNotVerifiedException("User is not verified");
        }
    }
}
