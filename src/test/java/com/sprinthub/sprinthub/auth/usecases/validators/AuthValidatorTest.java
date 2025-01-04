package com.sprinthub.sprinthub.auth.usecases.validators;

import com.sprinthub.sprinthub.auth.application.usecases.validators.CredentialsUserValidationRule;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertThrows;


public class AuthValidatorTest {

    private CredentialsUserValidationRule authValidator;

    @BeforeEach
    void setUp() {
        authValidator = new CredentialsUserValidationRule();
    }




}
