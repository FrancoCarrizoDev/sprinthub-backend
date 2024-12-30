package com.sprinthub.sprinthub.shared.exceptions;

public class InvalidCredentialsException extends ApplicationException  {
    public InvalidCredentialsException(String message) {
        super(message, ErrorCode.ERR_INVALID_CREDENTIALS);
    }
}