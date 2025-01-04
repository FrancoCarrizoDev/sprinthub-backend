package com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.exceptions;

import com.sprinthub.sprinthub.shared.exceptions.ApplicationException;
import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;

public class InvalidVerificationCodeException extends ApplicationException {

    public InvalidVerificationCodeException(String message) {
        super(message, ErrorCode.ERR_INVALID_VERIFICATION_CODE);
    }
}
