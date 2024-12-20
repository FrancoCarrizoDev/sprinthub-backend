package com.sprinthub.sprinthub.users.infraestructure.persistence;

import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository springDataRepository;

    public JpaUserRepository(SpringDataUserRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public UserJPA save(UserJPA userJPA) {
        return springDataRepository.save(userJPA);
    }

    @Override
    public Optional<UserJPA> findByEmail(String email) {
        return springDataRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserJPA> findById(UUID id) {
        return springDataRepository.findById(id);
    }
}
