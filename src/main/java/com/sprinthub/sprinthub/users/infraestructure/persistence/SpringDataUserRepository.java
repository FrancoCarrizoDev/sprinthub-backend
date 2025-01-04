package com.sprinthub.sprinthub.users.infraestructure.persistence;


import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends ListCrudRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
