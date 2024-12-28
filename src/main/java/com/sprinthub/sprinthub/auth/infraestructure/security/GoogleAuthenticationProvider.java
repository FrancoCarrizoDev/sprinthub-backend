package com.sprinthub.sprinthub.auth.infraestructure.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sprinthub.sprinthub.auth.application.dtos.OAuthUserDto;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private static final String CLIENT_ID = "890001511003-13onja4lfbciddspiim8ulsaet20ut3i.apps.googleusercontent.com";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials(); // Ahora tendrá el token válido

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String givenName = (String) payload.get("given_name");
                String familyName = (String) payload.get("family_name");
                String externalId = payload.getSubject();

                // Crear el objeto de autenticación
                OAuthUserDto user = new OAuthUserDto(email, name, externalId, familyName);
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            } else {
                throw new BadCredentialsException("Invalid Google ID token");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Error verifying Google ID token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
