package com.sprinthub.sprinthub.auth.application.dtos;

import lombok.Data;

@Data
public class OAuthUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String externalId;


    public OAuthUserDto(String email, String name, String externalId, String lastName) {
        this.email = email;
        this.firstName = name;
        this.externalId = externalId;
        this.lastName = lastName;
    }

}
