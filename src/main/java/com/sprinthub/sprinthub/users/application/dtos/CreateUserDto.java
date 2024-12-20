package com.sprinthub.sprinthub.users.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
