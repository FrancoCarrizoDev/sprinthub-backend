package com.sprinthub.sprinthub.auth.application.dtos;

import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @Email(message = ExceptionMessages.INVALID_EMAIL)
    @NotBlank(message = ExceptionMessages.EMPTY_EMAIL)
    private String email;

    @NotBlank(message = ExceptionMessages.EMPTY_PASSWORD)
    @Size(min = 6, message = ExceptionMessages.PASSWORD_TOO_SHORT)
    private String password;
}
