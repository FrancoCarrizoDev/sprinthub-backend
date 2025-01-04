package com.sprinthub.sprinthub.shared.exceptions;

import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.InvalidCredentialsException;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserInactiveException;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserNotVerifiedException;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserRegisteredWithGoogleException;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.exceptions.InvalidVerificationCodeException;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(new ApiResponse<>(false, null, e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Internal server error"));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotVerifiedException(UserNotVerifiedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(UserRegisteredWithGoogleException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserRegisteredWithGoogleException(UserRegisteredWithGoogleException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserInactiveException(UserInactiveException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidVerificationCodeException(InvalidVerificationCodeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, null, e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, null, ex.getBindingResult().getFieldError().getDefaultMessage(), ErrorCode.ERR_VALIDATION_FAILED));    }
}