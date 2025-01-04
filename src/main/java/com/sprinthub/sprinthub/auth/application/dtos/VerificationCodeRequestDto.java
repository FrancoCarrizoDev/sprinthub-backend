package com.sprinthub.sprinthub.auth.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerificationCodeRequestDto {
    private String email;
    private String verificationCode;
}
