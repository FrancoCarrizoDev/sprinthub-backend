package com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode;

import com.sprinthub.sprinthub.auth.application.dtos.VerificationCodeRequestDto;
import com.sprinthub.sprinthub.auth.application.usecases.VerifyVerificationCode.validators.VerificationCodeValidationRule;
import com.sprinthub.sprinthub.shared.exceptions.ExceptionMessages;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.domain.models.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;

import java.util.List;

public class VerificationCodeUseCase {

    private final UserRepository userRepository;
    private final List<VerificationCodeValidationRule> verificationCodeValidationRules;

    private final UserMapper userMapper;

    public VerificationCodeUseCase(UserRepository userRepository, List<VerificationCodeValidationRule> verificationCodeValidationRules, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.verificationCodeValidationRules = verificationCodeValidationRules;
        this.userMapper = userMapper;
    }

    public Boolean execute(VerificationCodeRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND)
        );

        UserEntity userEntity = userMapper.toEntity(user);

        verificationCodeValidationRules.forEach(rule -> rule.validate(userEntity, request));

        userEntity.getAuth().setVerified(true);

        user = userMapper.toDomain(userEntity);

        userRepository.save(user);

        return true;
    }
}
