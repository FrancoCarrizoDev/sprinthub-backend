package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class UserNotVerifiedException extends ApplicationException {

    public UserNotVerifiedException(String message) {
        super(message, ErrorCode.ERR_USER_NOT_VERIFIED);
    }
}
