package com.example.alura_challenge_forum_hub.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
    private String secret = "YOUR_SECRET"; // Substitua com o valor de 'jwt.secret' do seu application.properties
    private long expiration = 86400000; // Substitua com o valor de 'jwt.expiration' do seu application.properties

    public String gerarToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validarToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token)
                    .getSubject();
            return true;
        } catch (Exception e) {
            // Log the exception details for debugging purposes
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
        return jwt.getSubject();
    }
}
