package com.sprinthub.sprinthub.domain.repository;


import com.sprinthub.sprinthub.domain.models.UserJPA;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<UserJPA, UUID> {

    boolean existsByEmail(String email);

    Optional<UserJPA> findByEmail(String email);
}
