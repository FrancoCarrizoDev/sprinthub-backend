package com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.exceptions.UserRegisteredWithCredentialsException;
import com.sprinthub.sprinthub.users.domain.models.User;

public class RejectIfUserHasCredentialsAuthRule implements OAuthJwtAuthValidationRule {
    @Override
    public void validate(User user, OAuthUserDto request) {
        if (user.getUserAuth().getAuthProvider() == AuthProviderEnum.CREDENTIALS) {
            throw new UserRegisteredWithCredentialsException(
                    "Este usuario ya está registrado con credenciales. "
            );
        }
    }
}
