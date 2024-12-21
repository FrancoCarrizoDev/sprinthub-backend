package com.sprinthub.sprinthub.auth.adapters.controllers.in;


import com.sprinthub.sprinthub.auth.application.dtos.AuthResponseDto;
import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.infraestructure.security.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CheckOauthUserStatusUseCase checkOauthUserStatusUseCase;

    private final SaveOauthUserUseCase createOauthUserUseCase;

    public AuthController(CheckOauthUserStatusUseCase checkOauthUserStatusUseCase, SaveOauthUserUseCase createOauthUserUseCase) {
        this.checkOauthUserStatusUseCase = checkOauthUserStatusUseCase;
        this.createOauthUserUseCase = createOauthUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        String token = JwtTokenUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }



    @PostMapping("/signing")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?>oAuthSignIn(@RequestBody OAuthUserDto user) {

        HashMap<String, String> status = checkOauthUserStatusUseCase.execute(user.getEmail());

        if(status.get("status").equals("existing_user_no_google_id")){
            return ResponseEntity.badRequest().body("User already exists but without google id");
        }

        if(status.get("status").equals("new_user")){
            try {
                createOauthUserUseCase.execute(user);
                return ResponseEntity.ok("User created");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error saving user");
            }
        }


        return ResponseEntity.ok("User Signed In");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto user) {
        return ResponseEntity.ok("User registered");
    }
}
