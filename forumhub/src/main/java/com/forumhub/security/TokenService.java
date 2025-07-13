package com.forumhub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Algorithm algorithm;

    private JWTVerifier verifier;

    // Inicializa o algoritmo e o validador
    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String gerarToken(String username) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiration);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(agora)
                .withExpiresAt(expiracao)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public boolean validarToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
