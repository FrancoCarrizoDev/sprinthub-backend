package com.sprinthub.sprinthub.users.infraestructure.persistence;

import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository springDataRepository;

    private final UserMapper userMapper;

    public JpaUserRepository(SpringDataUserRepository springDataRepository, UserMapper userMapper) {
        this.springDataRepository = springDataRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntity = springDataRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(userMapper::toDomain);
    }
}
