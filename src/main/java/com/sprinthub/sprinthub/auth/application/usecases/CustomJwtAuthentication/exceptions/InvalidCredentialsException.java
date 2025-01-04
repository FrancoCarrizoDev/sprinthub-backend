package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException(String message) {
        super(message, ErrorCode.ERR_INVALID_CREDENTIALS);
    }
}