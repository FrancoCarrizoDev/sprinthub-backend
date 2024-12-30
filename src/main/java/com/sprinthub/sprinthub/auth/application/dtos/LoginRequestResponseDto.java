package com.sprinthub.sprinthub.auth.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginRequestResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String externalId;
}
