package com.sprinthub.sprinthub.auth.adapters.in;



import com.sprinthub.sprinthub.auth.application.dtos.*;
import com.sprinthub.sprinthub.auth.application.usecases.CustomJwtAuthentication.CustomJwtAuthenticationUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.OAuthJwtAuthenticationUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.VerificationCodeUseCase;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthJwtAuthenticationUseCase oAuthValidationUseCase;

    private final CustomJwtAuthenticationUseCase customJwtAuthenticationUseCase;

    private final VerificationCodeUseCase verifyVerificationCodeUseCase;




    public AuthController(  CustomJwtAuthenticationUseCase customJwtAuthenticationUseCase, OAuthJwtAuthenticationUseCase oAuthValidationUseCase, VerificationCodeUseCase verifyVerificationCodeUseCase) {
        this.customJwtAuthenticationUseCase = customJwtAuthenticationUseCase;
        this.oAuthValidationUseCase = oAuthValidationUseCase;
        this.verifyVerificationCodeUseCase = verifyVerificationCodeUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginRequestResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        ApiResponse<LoginRequestResponseDto> response = new ApiResponse<>(true, customJwtAuthenticationUseCase.execute(request), null);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<OAuthUserSigningResponse>> oAuthSignIn() {

        OAuthUserSigningResponse response = oAuthValidationUseCase.execute();
        ApiResponse<OAuthUserSigningResponse> apiResponse = new ApiResponse<>(true, response, null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/verification-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@RequestBody VerificationCodeRequestDto request) {
        verifyVerificationCodeUseCase.execute(request);
        return ResponseEntity.ok(new ApiResponse<>(true, true, null));
    }


}
