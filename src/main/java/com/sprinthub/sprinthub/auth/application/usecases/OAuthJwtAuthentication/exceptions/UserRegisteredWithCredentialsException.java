package com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class UserRegisteredWithCredentialsException extends ApplicationException {

    public UserRegisteredWithCredentialsException(String message) {
        super(message, ErrorCode.ERR_USER_REGISTERED_WITH_CREDENTIALS);
    }
}
