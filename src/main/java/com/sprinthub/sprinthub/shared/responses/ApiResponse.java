package com.sprinthub.sprinthub.shared.responses;

import com.sprinthub.sprinthub.shared.exceptions.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private ErrorCode errorCode; // Puede ser null para respuestas exitosas

    public ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = null; // No requerido en respuestas exitosas
    }

    public ApiResponse(boolean success, T data, String message, ErrorCode errorCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }


}