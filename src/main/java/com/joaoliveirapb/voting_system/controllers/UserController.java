package com.joaoliveirapb.voting_system.controllers;

import com.joaoliveirapb.voting_system.dtos.CreateUserRequestDTO;
import com.joaoliveirapb.voting_system.dtos.UserResponseDTO;
import com.joaoliveirapb.voting_system.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO request) {
        try {
            UserResponseDTO response = userService.createUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("[UserControler::createUser]: Erro ao criar usuário: " + e.getMessage());

            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }
}
