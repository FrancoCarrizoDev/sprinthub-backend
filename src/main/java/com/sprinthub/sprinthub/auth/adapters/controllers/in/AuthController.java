package com.sprinthub.sprinthub.auth.adapters.controllers.in;



import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestResponseDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.ValidateLoginUseCase;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.auth.infraestructure.security.CustomJwtAuthenticationProvider;
import com.sprinthub.sprinthub.shared.exceptions.RegisteredUserWithCredentialsException;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CheckOauthUserStatusUseCase checkOauthUserStatusUseCase;

    private final ValidateLoginUseCase validateLoginUseCase;
    private final SaveOauthUserUseCase createOauthUserUseCase;



    public AuthController(CheckOauthUserStatusUseCase checkOauthUserStatusUseCase, SaveOauthUserUseCase createOauthUserUseCase, ValidateLoginUseCase validateLoginUseCase) {
        this.checkOauthUserStatusUseCase = checkOauthUserStatusUseCase;
        this.createOauthUserUseCase = createOauthUserUseCase;
        this.validateLoginUseCase = validateLoginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginRequestResponseDto>> login(@RequestBody LoginRequestDto request) {


        ApiResponse<LoginRequestResponseDto> response = new ApiResponse<>(true, validateLoginUseCase.execute(request), null);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<OAuthUserSigningResponse>> oAuthSignIn() {

        OAuthUserDto user = (OAuthUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if(user.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        OAuthUserStatus status = checkOauthUserStatusUseCase.execute(user.getEmail());

        if(status == OAuthUserStatus.NEW_USER) {
            createOauthUserUseCase.execute(user);
        }

        if(status == OAuthUserStatus.EXISTING_USER_NO_GOOGLE_ID){
            throw new RegisteredUserWithCredentialsException("User already exists with credentials");
        }

        OAuthUserSigningResponse bodyResponse = new OAuthUserSigningResponse(status, user.getEmail(), user.getFirstName(), user.getLastName());
        ApiResponse<OAuthUserSigningResponse> response = new ApiResponse<>(true, bodyResponse, null);
        return ResponseEntity.ok(response);
    }


}
