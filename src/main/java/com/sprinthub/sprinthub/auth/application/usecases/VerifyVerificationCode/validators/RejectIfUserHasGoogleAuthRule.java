package com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.dtos.VerificationCodeRequestDto;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.exceptions.UserRegisteredWithGoogleException;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;

public class RejectIfUserHasGoogleAuthRule implements VerificationCodeValidationRule{

        @Override
        public void validate(UserEntity user, VerificationCodeRequestDto request) {
            if (user.getUserAuth().getAuthProvider() != AuthProviderEnum.CREDENTIALS) {
                throw new UserRegisteredWithGoogleException(ExceptionMessages.USER_REGISTERED_WITH_GOOGLE);
            }
        }
}
