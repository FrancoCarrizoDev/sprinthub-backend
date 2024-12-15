package com.sprinthub.sprinthub.dtos;

import lombok.Data;

@Data
public class OAuthUserDto {
    private String email;
    private String name;
    private String externalId;


    public OAuthUserDto(String email, String name, String externalId) {
        this.email = email;
        this.name = name;
        this.externalId = externalId;
    }

}
