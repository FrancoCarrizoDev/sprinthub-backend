package com.sprinthub.sprinthub.auth.adapters.controllers.in;



import com.sprinthub.sprinthub.auth.application.dtos.LoginRequestDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.shared.responses.ApiResponse;
import com.sprinthub.sprinthub.auth.application.usecases.CheckOauthUserStatusUseCase;
import com.sprinthub.sprinthub.auth.application.usecases.SaveOauthUserUseCase;
import com.sprinthub.sprinthub.auth.infraestructure.security.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        String token = JwtTokenUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(true);
    }



    @PostMapping("/signin")
    @PreAuthorize("isAuthenticated()")
    //public ResponseEntity<ApiResponse<OAuthUserSigningResponse>> oAuthSignIn(@RequestBody OAuthUserDto user) {

    public ResponseEntity<?> oAuthSignIn(@RequestBody Object user) {
        return ResponseEntity.ok(true);
        /*OAuthUserStatus status = checkOauthUserStatusUseCase.execute(user.getEmail());
        OAuthUserSigningResponse response = new OAuthUserSigningResponse(status, user.getEmail(), user.getFirstName(), user.getLastName());
        switch (status) {
            case NEW_USER:
                try {
                    createOauthUserUseCase.execute(user);
                    return ResponseEntity.ok(
                            new ApiResponse<>(true, response, null)
                    );
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(
                            new ApiResponse<>(false, response, "Error saving user")
                    );
                }

            case EXISTING_USER_NO_GOOGLE_ID:
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(false, response, status.getMessage()
                ));

            case EXISTING_USER_WITH_GOOGLE_ID:
            default:
                return ResponseEntity.ok(
                        new ApiResponse<>(true, response, null)
                );

        }

         */
    }


}
