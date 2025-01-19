package com.sprinthub.sprinthub.auth.application.usecases.validators;

import com.sprinthub.sprinthub.users.domain.models.User;

public interface UserValidationRule {
    void validate(User user);
}