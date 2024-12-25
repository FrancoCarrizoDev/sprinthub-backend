package com.sprinthub.sprinthub.auth.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String externalId;
}
