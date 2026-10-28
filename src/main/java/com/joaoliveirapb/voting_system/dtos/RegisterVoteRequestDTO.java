package com.joaoliveirapb.voting_system.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterVoteRequestDTO(
        @NotNull(message = "Usuário é obrigatório")
        UUID userId,

        @NotNull(message = "Opção de votação é obrigatório")
        UUID votingOptionId
) {}
