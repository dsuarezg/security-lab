package com.ironhack.spring_security.services;

import com.ironhack.spring_security.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("Generate a JWT token")
    public void testGenerateToken() {
        String token = jwtService.generateToken("testUser", "[ROLE_ADMIN]");
        System.out.println("Generated token: " + token);
        assertNotNull(token);
    }

}
