package com.ironhack.spring_security.services;

import com.ironhack.spring_security.models.Role;
import com.ironhack.spring_security.models.User;
import com.ironhack.spring_security.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ironhack.spring_security.models.ERole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("123456");
//        Role role = new Role();
//        role.setName(ROLE_USER);
//
//        user.getRoles().add(role);
        System.out.println("User created: " + user);

        userService.saveUser(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

    @Test
    @DisplayName("Encription of password successful")
    public void testEncriptionOfPasswordSuccessful() {
        assertTrue(user.getPassword().startsWith("$2a$"));// BCrypt password starts with $2a$
        System.out.println("Password encrypted: " + user.getPassword());
    }
}