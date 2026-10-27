package com.joaoliveirapb.voting_system.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateVotingOptionDTO(
        @NotBlank(message = "Nome da opção de votação é obrigatório")
        String name
) {}
