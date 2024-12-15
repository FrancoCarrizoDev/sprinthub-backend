package com.sprinthub.sprinthub.controllers;


import com.sprinthub.sprinthub.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }




    @PostMapping("/signing")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?>oAuthSignIn(@RequestBody OAuthUserDto user) {

        HashMap<String, String> status = authService.checkOauthUserStatus(user.getEmail());

        if(status.get("status").equals("existing_user_no_google_id")){
            return ResponseEntity.badRequest().body("User already exists but without google id");
        }

        if(status.get("status").equals("new_user")){
            try {
                authService.saveOauthUser(user);
                return ResponseEntity.ok("User created");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error saving user");
            }
        }


        return ResponseEntity.ok("User Signed In");
    }
}
