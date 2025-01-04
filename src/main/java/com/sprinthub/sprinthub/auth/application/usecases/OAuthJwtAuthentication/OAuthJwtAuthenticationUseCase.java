package com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserSigningResponse;
import com.sprinthub.sprinthub.auth.application.usecases.OAuthJwtAuthentication.validators.OAuthJwtAuthValidationRule;
import com.sprinthub.sprinthub.auth.application.usecases.validators.UserValidationRule;
import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.auth.domain.models.UserAuthJPA;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.domain.models.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
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
            UserEntity newUser = createUserForOAuth(userDTO);

            userRepository.save(userMapper.toDomain(newUser));
            return new OAuthUserSigningResponse(OAuthUserStatus.NEW_USER, userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName());
        }


        User userDomain = optionalUserJPA.get();
        UserEntity userJPA = userMapper.toEntity(userDomain);

        for (UserValidationRule rule : userValidationRules) {
            rule.validate(userJPA);
        }

        for (OAuthJwtAuthValidationRule rule : oauthJwtAuthValidationRules) {
            rule.validate(userJPA, userDTO);
        }

        OAuthUserStatus status = userJPA.getAuth().getExternalId() == null
                ? OAuthUserStatus.EXISTING_USER_NO_GOOGLE_ID
                : OAuthUserStatus.EXISTING_USER_WITH_GOOGLE_ID;

        // 6) Responder
        return new OAuthUserSigningResponse(
                status,
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName()
        );
    }

    private UserEntity createUserForOAuth(OAuthUserDto user) {
        UserEntity userJPA =  new UserEntity();
        userJPA.setEmail(user.getEmail());
        userJPA.setFirstName(user.getFirstName());
        userJPA.setLastName(null);
        UserAuthJPA userAuth = new UserAuthJPA();
        userAuth.setExternalId(user.getExternalId());
        userAuth.setAuthProvider(AuthProviderEnum.GOOGLE);
        userAuth.setUser(userJPA);
        userAuth.setLastLogin(LocalDateTime.now());
        userAuth.setVerified(true);
        userJPA.setAuth(userAuth);

        try {
            return userJPA;
        } catch (Exception e) {
            throw new RuntimeException("Error saving user");
        }
    }
}
