package com.ironhack.spring_security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ironhack.spring_security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "secret_key";

    public String generateToken(String username, String roles) {
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getSubject();
    }

    public String getRoles(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

        return jwt.getClaim("roles").asString();

    }
}
