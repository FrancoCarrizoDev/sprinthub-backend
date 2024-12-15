package com.sprinthub.sprinthub.repositories;


import com.sprinthub.sprinthub.models.UserJPA;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<UserJPA, Long> {

    boolean existsByEmail(String email);

    Optional<UserJPA> findByEmail(String email);
}
