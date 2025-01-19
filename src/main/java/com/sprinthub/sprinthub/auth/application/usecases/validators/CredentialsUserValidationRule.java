package com.sprinthub.sprinthub.auth.application.usecases.validators;

import com.sprinthub.sprinthub.auth.application.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserRegisteredWithGoogleException;
import com.sprinthub.sprinthub.users.domain.models.User;

import java.util.Objects;

public class CredentialsUserValidationRule implements UserValidationRule {

    @Override
    public void validate(User model) {
        if (Objects.equals(model.getUserAuth().getAuthProvider().toString(), AuthProviderEnum.GOOGLE.toString())) {
            throw new UserRegisteredWithGoogleException("Invalid credentials for Google registered user");
        }
    }
}
