package com.joaoliveirapb.voting_system.services;

import com.joaoliveirapb.voting_system.dtos.CreateUserRequestDTO;
import com.joaoliveirapb.voting_system.entities.User;
import com.joaoliveirapb.voting_system.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(CreateUserRequestDTO request) {
        Optional<User> userWithSameEmail = userRepository.findByEmail(request.email());

        if (userWithSameEmail.isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        User newUser = new User(request.name(), request.email());

        return userRepository.save(newUser);
    }
}
