package com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators;

import com.sprinthub.sprinthub.auth.application.dtos.VerificationCodeRequestDto;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.exceptions.InvalidVerificationCodeException;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public class VerificationCodeMatchesValidationRule implements VerificationCodeValidationRule{

    @Override
    public void validate(UserEntity user, VerificationCodeRequestDto request) {
        if (!user.getAuth().getVerificationCode().equals(request.getVerificationCode())) {
            throw new InvalidVerificationCodeException(ExceptionMessages.INVALID_VERIFICATION_CODE);
        }
    }
}
