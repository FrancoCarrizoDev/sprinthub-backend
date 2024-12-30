package com.sprinthub.sprinthub.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt.google")
public class GoogleJwtConfig {
    private String client_id;

    // Getters y setters
    public String getClientId() {
        return client_id;
    }

    public void setClientId(String clientId) {
        this.client_id = clientId;
    }


}
