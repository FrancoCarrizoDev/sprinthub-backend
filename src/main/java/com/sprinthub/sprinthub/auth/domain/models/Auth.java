package com.sprinthub.sprinthub.auth.domain.models;

import java.time.LocalDateTime;
import java.util.Random;

public interface Auth {

    String generateVerificationCode();

    LocalDateTime calculateExpirationTime();
}