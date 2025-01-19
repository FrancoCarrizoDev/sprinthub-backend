package com.sprinthub.sprinthub.messaging.adapters.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinthub.sprinthub.messaging.domain.models.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerAdapter {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;


    public KafkaProducerAdapter(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> void send(String topic, String key, T message) {
        String messageSerialized = serializeEvent(message);
        kafkaTemplate.send(topic, key, messageSerialized);
    }

    private <T> String serializeEvent(T event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }

}
