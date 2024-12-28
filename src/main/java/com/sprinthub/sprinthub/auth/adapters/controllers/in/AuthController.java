package com.sprinthub.sprinthub.auth.adapters.controllers.in;



import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


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
        String token = CustomJwtAuthenticationProvider.generateToken(request.getEmail());
        return ResponseEntity.ok(token);
    }



    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<OAuthUserSigningResponse>> oAuthSignIn() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuthUserDto user = (OAuthUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        if(user.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        OAuthUserStatus status = checkOauthUserStatusUseCase.execute(user.getEmail());

        if(status == OAuthUserStatus.NEW_USER) {

            createOauthUserUseCase.execute(user);
        }

        OAuthUserSigningResponse bodyResponse = new OAuthUserSigningResponse(status, user.getEmail(), user.getFirstName(), user.getLastName());
        return ResponseEntity.ok(new ApiResponse(true, bodyResponse, null));



    }


}
