package com.joaoliveirapb.voting_system.dtos;

import com.joaoliveirapb.voting_system.entities.User;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email) {
    public UserResponseDTO(User userEntity) {
        this(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}
