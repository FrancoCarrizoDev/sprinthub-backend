package com.sprinthub.sprinthub.users.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinthub.sprinthub.auth.domain.models.Auth;
import com.sprinthub.sprinthub.auth.domain.services.PasswordHasher;
import com.sprinthub.sprinthub.messaging.domain.models.UserCreatedEvent;
import com.sprinthub.sprinthub.messaging.adapters.out.KafkaProducerAdapter;
import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.mappers.UserMapper;
import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CreateNewUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordHasher passwordHasher;
    private final KafkaProducerAdapter kafkaProducerAdapter;
    private final Logger logger = LoggerFactory.getLogger(CreateNewUserUseCase.class);
    private final ObjectMapper objectMapper;

    public CreateNewUserUseCase(UserRepository userRepository, UserMapper userMapper, PasswordHasher passwordHasher, KafkaProducerAdapter kafkaProducerAdapter, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordHasher = passwordHasher;
        this.kafkaProducerAdapter = kafkaProducerAdapter;
        this.objectMapper = objectMapper;
    }

    public void execute(CreateUserDto createUserDto) {
        try {
            if (userRepository.existsByEmail(createUserDto.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }

            // 1. Hash de contraseña
            createUserDto.setPassword(passwordHasher.hashPassword(createUserDto.getPassword()));

            // 2. Crear usuario desde DTO
            UserJPA user = userMapper.fromCreateDTO(createUserDto);

            // 3. Generar código de verificación
            Auth auth = new Auth();
            String verificationCode = auth.generateVerificationCode();
            LocalDateTime expirationTime = auth.calculateExpirationTime();
            user.getAuth().setVerificationCode(verificationCode);
            user.getAuth().setVerificationExpiresAt(expirationTime);

            // 4. Guardar usuario
            userRepository.save(user);

            // 5. Publicar evento en Kafka
            UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getEmail(), user.getAuth().getVerificationCode());
            kafkaProducerAdapter.send("user.created", event.getUserId().toString(), serializeEvent(event));
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new RuntimeException("Error saving user");
        }
    }


    private String serializeEvent(UserCreatedEvent event) {
        try {
            return objectMapper.writeValueAsString(event); // Usa el ObjectMapper inyectado
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }
}
