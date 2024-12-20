package com.sprinthub.sprinthub.users.infraestructure.persistence;


import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends ListCrudRepository<UserJPA, UUID> {

    boolean existsByEmail(String email);

    Optional<UserJPA> findByEmail(String email);
}
