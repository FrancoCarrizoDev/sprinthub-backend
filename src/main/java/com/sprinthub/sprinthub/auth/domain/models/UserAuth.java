package com.sprinthub.sprinthub.auth.domain.models;

import com.sprinthub.sprinthub.auth.application.dtos.AuthProviderEnum;
import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
public class UserAuth {
    private UUID id;
    private User user;
    private AuthProviderEnum authProvider;
    private String passwordHash;
    private String externalId;
    private String verificationCode;
    private LocalDateTime verificationExpiresAt;
    private boolean isVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserAuth() {
    }

    public UserAuth(UUID id, User user, AuthProviderEnum authProvider, String passwordHash, String externalId, String verificationCode, LocalDateTime verificationExpiresAt, boolean isVerified, LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.authProvider = authProvider;
        this.passwordHash = passwordHash;
        this.externalId = externalId;
        this.verificationCode = verificationCode;
        this.verificationExpiresAt = verificationExpiresAt;
        this.isVerified = isVerified;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthProviderEnum getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProviderEnum authProvider) {
        this.authProvider = authProvider;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationExpiresAt() {
        return verificationExpiresAt;
    }

    public void setVerificationExpiresAt(LocalDateTime verificationExpiresAt) {
        this.verificationExpiresAt = verificationExpiresAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}