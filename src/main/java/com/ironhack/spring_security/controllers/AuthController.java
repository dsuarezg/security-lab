package com.ironhack.spring_security.controllers;

import com.ironhack.spring_security.models.ERole;
import com.ironhack.spring_security.models.Role;
import com.ironhack.spring_security.models.User;
import com.ironhack.spring_security.services.JwtService;
import com.ironhack.spring_security.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<?> login (@RequestBody User user) {
        Optional<User> optionalUser = userService.getByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (userService.passwordIsValid(existingUser, user.getPassword())) {

                List<ERole> roleNames = existingUser.getRoles().stream()
                        .map(Role::getName)
                        .toList();

                String token = jwtService.generateToken(existingUser.getUsername(), roleNames.toString());

                //
//                AuthResponseDto responseDto = new AuthResponseDto();
//                responseDto.setToken(token);
//                responseDto.setUsername(existingUser.getUsername());
//                responseDto.setRoles(roleNames);

               return ResponseEntity.ok(token);
              //  return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
