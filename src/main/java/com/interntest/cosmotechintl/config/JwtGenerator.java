package com.interntest.cosmotechintl.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final JwtProperties jwtProperties;

    public String createToken(long userId, String email, String username, List<String> roles) {
     return JWT.create()
             .withSubject(String.valueOf(userId))
             .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
             .withClaim("e",email)
             .withClaim("un", username)
             .withClaim("r",roles)
             .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));

    }
}
