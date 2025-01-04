package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class UserRegisteredWithGoogleException extends ApplicationException {

    public UserRegisteredWithGoogleException(String message) {
        super(message, ErrorCode.ERR_USER_REGISTERED_WITH_GOOGLE);
    }
}
