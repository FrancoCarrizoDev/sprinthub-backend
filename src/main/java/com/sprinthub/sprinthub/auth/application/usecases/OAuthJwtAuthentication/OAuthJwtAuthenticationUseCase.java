package com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators.OAuthJwtAuthValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.validators.UserValidationRule;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.auth.domain.models.UserAuth;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OAuthJwtAuthenticationUseCase {

    private final UserRepository userRepository;
    private final List<UserValidationRule> userValidationRules;
    private final List<OAuthJwtAuthValidationRule> oauthJwtAuthValidationRules;

    private final UserMapper userMapper;


    public OAuthJwtAuthenticationUseCase(UserRepository userRepository, List<UserValidationRule> userValidationRules, List<OAuthJwtAuthValidationRule> oauthJwtAuthValidationRules, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userValidationRules = userValidationRules;
        this.oauthJwtAuthValidationRules = oauthJwtAuthValidationRules;
        this.userMapper = userMapper;
    }

    public OAuthUserSigningResponse execute() {

        OAuthUserDto userDTO = (OAuthUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUserJPA = userRepository.findByEmail(userDTO.getEmail());


        if(optionalUserJPA.isEmpty()){
            User newUser = userMapper.fromOAuthUserDTO(userDTO);

            userRepository.save(newUser);
            return new OAuthUserSigningResponse(OAuthUserStatus.NEW_USER, userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName());
        }


        User userDomain = optionalUserJPA.get();

        for (UserValidationRule rule : userValidationRules) {
            rule.validate(userDomain);
        }

        for (OAuthJwtAuthValidationRule rule : oauthJwtAuthValidationRules) {
            rule.validate(userDomain, userDTO);
        }

        OAuthUserStatus status = userDomain.getUserAuth().getExternalId() == null
                ? OAuthUserStatus.EXISTING_USER_NO_GOOGLE_ID
                : OAuthUserStatus.EXISTING_USER_WITH_GOOGLE_ID;


        return new OAuthUserSigningResponse(
                status,
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName()
        );
    }


}
