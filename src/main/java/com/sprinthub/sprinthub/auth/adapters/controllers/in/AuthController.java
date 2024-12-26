package com.sprinthub.sprinthub.auth.adapters.controllers.in;



import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtUtil;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
        String token = CustomJwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(true);
    }



    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<OAuthUserSigningResponse>> oAuthSignIn() {



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        String name = (String) details.get("name");
        String givenName = (String) details.get("givenName");
        String familyName = (String) details.get("familyName");
        String externalId = (String) details.get("externalId");

        if(email == null) {
            return ResponseEntity.badRequest().build();
        }

        OAuthUserStatus status = checkOauthUserStatusUseCase.execute(email);

        if(status == OAuthUserStatus.NEW_USER) {
            OAuthUserDto user = new OAuthUserDto(email,name, externalId, familyName);
            createOauthUserUseCase.execute(user);
        }

        OAuthUserSigningResponse bodyResponse = new OAuthUserSigningResponse(status, email,name, familyName);
        return ResponseEntity.ok(new ApiResponse(true, bodyResponse, null));



    }


}
