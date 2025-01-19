package com.sprinthub.sprinthub.auth.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginRequestResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String externalId;
}
