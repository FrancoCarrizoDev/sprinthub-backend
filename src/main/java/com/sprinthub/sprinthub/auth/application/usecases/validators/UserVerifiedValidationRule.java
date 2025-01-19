package com.sprinthub.sprinthub.auth.application.usecases.validators;


import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserNotVerifiedException;
import com.sprinthub.sprinthub.users.domain.models.User;

public class UserVerifiedValidationRule implements UserValidationRule {
    @Override
    public void validate(User user) {
        if (!user.getUserAuth().isVerified()) {
            throw new UserNotVerifiedException("User is not verified");
        }
    }
}
