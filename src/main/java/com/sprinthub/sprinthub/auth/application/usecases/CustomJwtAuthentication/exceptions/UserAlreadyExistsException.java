package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class UserAlreadyExistsException extends ApplicationException {

    public UserAlreadyExistsException(String message) {
        super(message, ErrorCode.ERR_USER_ALREADY_EXISTS);
    }
}
