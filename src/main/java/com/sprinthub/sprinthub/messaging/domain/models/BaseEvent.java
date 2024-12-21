package com.sprinthub.sprinthub.messaging.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEvent {

    private String eventId;
    private LocalDateTime timestamp;

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

    public String getEventId() {
        return eventId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


}
