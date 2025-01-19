package com.sprinthub.sprinthub.users.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String verificationCode;
    private LocalDateTime verificationExpiresAt;
}
