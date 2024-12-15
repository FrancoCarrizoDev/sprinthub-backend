package com.sprinthub.sprinthub.services;



import com.sprinthub.sprinthub.dtos.OAuthUserDto;

import java.util.HashMap;

public interface AuthService {
    HashMap<String, String> checkEmail(String email);

    HashMap<String, String> checkOauthUserStatus(String email);



    void saveOauthUser(OAuthUserDto user);
}