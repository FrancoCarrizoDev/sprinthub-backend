package com.sprinthub.sprinthub.users.domain.repository;
import com.sprinthub.sprinthub.users.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);
    Optional<User> findById(UUID email);
    Optional<User> findByEmail(String email);

    //save method
    User save(User userJPA);
}
