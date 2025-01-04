package com.sprinthub.sprinthub.auth.domain.models;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_auth")
@Getter
@Setter
public class UserAuthJPA {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false) // Cambiado de ManyToOne a OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // unique=true garantiza unicidad en la base de datos
    private UserEntity user;

    @Column(name = "auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProvider = AuthProviderEnum.CREDENTIALS;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "verification_code", length = 6)
    private String verificationCode;

    @Column(name = "verification_expires_at")
    private LocalDateTime verificationExpiresAt;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and setters

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}