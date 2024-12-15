package com.sprinthub.sprinthub.services.imp;

import com.sprinthub.sprinthub.dtos.OAuthUserDto;
import com.sprinthub.sprinthub.models.UserAuthJPA;
import com.sprinthub.sprinthub.models.UserJPA;
import com.sprinthub.sprinthub.repositories.UserRepository;
import com.sprinthub.sprinthub.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    // add logger
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    public AuthServiceImpl(UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public HashMap<String, String> checkEmail(String email) {
        HashMap<String, String> response = new HashMap<>();
        boolean exists = userRepository.existsByEmail(email);
        response.put("exists", String.valueOf(exists));
        return response;
    }

    @Override
    public HashMap<String, String> checkOauthUserStatus(String email) {
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


    @Override
    public void saveOauthUser(OAuthUserDto user) {
        UserJPA userJPA =  new UserJPA();
        userJPA.setEmail(user.getEmail());
        userJPA.setFirstName(user.getName());
        userJPA.setLastName(null);
        UserAuthJPA userAuth = new UserAuthJPA();
        userAuth.setExternalId(user.getExternalId());
        userAuth.setAuthProvider("google");
        userAuth.setUser(userJPA);
        userAuth.setLastLogin(LocalDateTime.now());

        userJPA.setAuth(userAuth);

        try {
            userRepository.save(userJPA);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user");
        }
    }
}
