package com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators;

import com.sprinthub.sprinthub.auth.application.dtos.VerificationCodeRequestDto;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public interface VerificationCodeValidationRule {
    void validate(UserEntity user, VerificationCodeRequestDto request);
}
