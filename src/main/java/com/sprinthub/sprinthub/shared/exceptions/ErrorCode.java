package com.sprinthub.sprinthub.shared.exceptions;

public enum ErrorCode {
    ERR_INVALID_CREDENTIALS("ERR_INVALID_CREDENTIALS"),
    ERR_USER_NOT_FOUND("ERR_USER_NOT_FOUND"),
    ERR_USER_NOT_VERIFIED("ERR_USER_NOT_VERIFIED"),
    ERR_USER_INACTIVE("ERR_USER_INACTIVE"),
    ERR_USER_ALREADY_EXISTS("ERR_USER_ALREADY_EXISTS"),
    ERR_USER_REGISTERED_WITH_CREDENTIALS("ERR_USER_REGISTERED_WITH_CREDENTIALS"),
    ERR_USER_REGISTERED_WITH_GOOGLE("ERR_USER_REGISTERED_WITH_GOOGLE"),
    ERR_VALIDATION_FAILED("ERR_VALIDATION_FAILED"),
    ERR_INVALID_VERIFICATION_CODE("ERR_INVALID_VERIFICATION_CODE"),
    ERR_INTERNAL_SERVER_ERROR("ERR_INTERNAL_SERVER_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ErrorCode getCodeFromValue(String value){
        for(ErrorCode e : ErrorCode.values()){
            if(e.getCode().equals(value)){
                return e;
            }
        }
        return null;
    }
}