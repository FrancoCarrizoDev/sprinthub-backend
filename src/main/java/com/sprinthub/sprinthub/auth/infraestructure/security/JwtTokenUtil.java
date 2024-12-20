package com.sprinthub.sprinthub.auth.infraestructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    private static final String SECRET_KEY = "EstaEsUnaClaveSecretaMuySegura12345678";

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Email del usuario
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
}