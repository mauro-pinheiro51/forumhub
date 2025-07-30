package com.forumhub.apitopicos.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(String login) {
        return JWT.create()
                .withSubject(login)
                .withExpiresAt(Instant.now().plus(expiration, ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC256(secret));
    }
}