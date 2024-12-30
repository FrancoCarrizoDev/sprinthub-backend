package com.sprinthub.sprinthub.auth.application.dtos;

public enum AuthProviderEnum {
    GOOGLE,
    CREDENTIALS;

    public static AuthProviderEnum fromString(String provider) {
        for (AuthProviderEnum authProvider : AuthProviderEnum.values()) {
            if (authProvider.name().equalsIgnoreCase(provider)) {
                return authProvider;
            }
        }
        return null;
    }
}