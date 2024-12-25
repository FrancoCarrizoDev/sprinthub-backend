package com.sprinthub.sprinthub.auth.domain.enums;

public enum OAuthUserStatus {
    NEW_USER("new_user", "User does not exist, ready to create a new one."),
    EXISTING_USER_NO_GOOGLE_ID("existing_user_no_google_id", "User exists but does not have a Google ID linked."),
    EXISTING_USER_WITH_GOOGLE_ID("existing_user_with_google_id", "User exists and is already linked with Google ID.");

    private final String value;
    private final String message;

    OAuthUserStatus(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
