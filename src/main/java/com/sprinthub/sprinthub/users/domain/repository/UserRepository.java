package com.sprinthub.sprinthub.users.domain.repository;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);
    Optional<UserJPA> findById(UUID email);
    Optional<UserJPA> findByEmail(String email);

    //save method
    UserJPA save(UserJPA userJPA);
}
