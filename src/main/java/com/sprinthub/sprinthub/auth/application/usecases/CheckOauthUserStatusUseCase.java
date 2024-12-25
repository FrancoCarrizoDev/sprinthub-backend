package com.sprinthub.sprinthub.auth.application.usecases;

import com.sprinthub.sprinthub.auth.domain.enums.OAuthUserStatus;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

import java.util.Optional;

public class CheckOauthUserStatusUseCase {

    private final UserRepository userRepository;

    public CheckOauthUserStatusUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OAuthUserStatus execute(String email) {
        Optional<UserJPA> userJPA = userRepository.findByEmail(email);

        if (userJPA.isEmpty()) {
            return OAuthUserStatus.NEW_USER;
        } else {
            UserJPA user = userJPA.get();
            if (user.getAuth().getExternalId() == null) {
                return OAuthUserStatus.EXISTING_USER_NO_GOOGLE_ID;
            } else {
                return OAuthUserStatus.EXISTING_USER_WITH_GOOGLE_ID;
            }
        }
    }

}
