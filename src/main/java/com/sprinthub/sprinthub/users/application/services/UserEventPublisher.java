package com.sprinthub.sprinthub.users.application.services;

import com.sprinthub.sprinthub.messaging.adapters.out.KafkaProducerAdapter;
import com.sprinthub.sprinthub.messaging.domain.models.UserCreatedEvent;
import com.sprinthub.sprinthub.users.domain.models.User;

public class UserEventPublisher {
    private final KafkaProducerAdapter kafkaProducerAdapter;

    public UserEventPublisher(KafkaProducerAdapter kafkaProducerAdapter) {
        this.kafkaProducerAdapter = kafkaProducerAdapter;
    }

    public void publishUserCreatedEvent(User user) {
        UserCreatedEvent event = new UserCreatedEvent(
                user.getId(),
                user.getEmail(),
                user.getUserAuth().getVerificationCode()
        );
        kafkaProducerAdapter.send("user.created", event.getUserId().toString(), event);
    }
}
