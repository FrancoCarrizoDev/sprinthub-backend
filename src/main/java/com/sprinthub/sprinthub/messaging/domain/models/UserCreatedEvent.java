package com.sprinthub.sprinthub.messaging.domain.models;

import java.util.UUID;

public class UserCreatedEvent extends BaseEvent {

    private UUID userId;
    private String email;
    private String verificationCode;

    public UserCreatedEvent(UUID userId, String email, String verificationCode) {
        super(); // Llama al constructor de BaseEvent para generar eventId y timestamp
        this.userId = userId;
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }


}