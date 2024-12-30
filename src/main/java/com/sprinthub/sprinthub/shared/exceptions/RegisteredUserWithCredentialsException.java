package com.sprinthub.sprinthub.shared.exceptions;

public class RegisteredUserWithCredentialsException extends ApplicationException{

    public RegisteredUserWithCredentialsException(String message) {
        super(message, ErrorCode.ERR_USER_REGISTERED_WITH_CREDENTIALS);
    }
}
