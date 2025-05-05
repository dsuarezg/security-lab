package com.ironhack.spring_security.services;

import com.ironhack.spring_security.models.User;
import com.ironhack.spring_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean passwordIsValid(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
