package com.ironhack.spring_security.controllers;

//import com.ironhack.spring_security.models.User;
//import com.ironhack.spring_security.repositories.UserRepository;
//import com.ironhack.spring_security.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BasicController {

//    @Autowired
//    public UserRepository userRepository;

    @GetMapping("/cosas-importantes")
    @Operation(summary = "Cosas importantes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cosas importantes"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<String> cosasImportantes() {
        return ResponseEntity.ok("Cosas importantes");
    }

    @GetMapping("/public/users")
//    @Operation(summary = "Public users")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Public users"),
//            @ApiResponse(responseCode = "404", description = "Not Found")})
//    public ResponseEntity<List<User>> publicUsers() {
//        List<User> users = userRepository.findAll();
//        return ResponseEntity.ok(users);
//    }

    public ResponseEntity<String> publicUsers() {
        return ResponseEntity.ok("Esta es una ruta pública");
    }

    @GetMapping("/admin")
    @Operation(summary = "Admin route")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Admin route"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<String> adminRoute() {
        return ResponseEntity.ok("Admin");
    }


}

