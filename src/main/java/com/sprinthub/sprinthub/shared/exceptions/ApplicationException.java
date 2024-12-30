package com.sprinthub.sprinthub.shared.exceptions;

public abstract class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
