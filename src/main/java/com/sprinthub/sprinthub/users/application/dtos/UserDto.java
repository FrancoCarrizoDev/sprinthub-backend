package com.sprinthub.sprinthub.users.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isActive;
    private String authProvider;
    private String externalId;
    private boolean isVerified;
    private LocalDateTime lastLogin;
}
