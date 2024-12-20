package com.sprinthub.sprinthub.auth.application.usecases;

import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

import java.util.HashMap;
import java.util.Optional;

public class CheckOauthUserStatusUseCase {

    private final UserRepository userRepository;

    public CheckOauthUserStatusUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HashMap<String, String> execute(String email) {
        HashMap<String, String> response = new HashMap<>();
        Optional<UserJPA> userJPA = userRepository.findByEmail(email);

        if(userJPA.isEmpty()){
            response.put("status", "new_user");
        } else {
            UserJPA user = userJPA.get();

            if(user.getAuth().getExternalId() == null){
                response.put("status", "existing_user_no_google_id");
            } else {
                response.put("status", "existing_user_with_google_id");
            }
        }

        return response;
    }

}
