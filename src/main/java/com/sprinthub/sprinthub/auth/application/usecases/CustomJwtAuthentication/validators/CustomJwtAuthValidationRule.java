package com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.validators;

import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.users.domain.models.User;

public interface CustomJwtAuthValidationRule {
    void validate(User user, LoginRequestDto request);

}
