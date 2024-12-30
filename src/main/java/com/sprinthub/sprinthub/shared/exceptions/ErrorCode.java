package com.sprinthub.sprinthub.shared.exceptions;

public enum ErrorCode {
    ERR_INVALID_CREDENTIALS("ERR_INVALID_CREDENTIALS"),
    ERR_USER_NOT_FOUND("ERR_USER_NOT_FOUND"),
    ERR_USER_REGISTERED_WITH_CREDENTIALS("ERR_USER_REGISTERED_WITH_CREDENTIALS"),
    ERR_VALIDATION_FAILED("ERR_VALIDATION_FAILED"),
    ERR_INTERNAL_SERVER_ERROR("ERR_INTERNAL_SERVER_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}