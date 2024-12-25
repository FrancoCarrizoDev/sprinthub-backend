package com.sprinthub.sprinthub.auth.application.dtos;

import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserSigningResponse {
    private OAuthUserStatus status;
    private String email;
    private String firstName;
    private String lastName;

}