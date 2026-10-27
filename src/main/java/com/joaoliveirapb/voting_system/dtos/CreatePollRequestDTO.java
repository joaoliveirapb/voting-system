package com.joaoliveirapb.voting_system.dtos;

import com.joaoliveirapb.voting_system.entities.PollStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreatePollRequestDTO(
        @NotNull(message = "Usuário é obrigatório")
        UUID userId,

        @NotBlank(message = "Título é obrigatório")
        String title,

        @NotBlank(message = "Questão é obrigatório")
        String question,

        @NotNull(message = "Status é obrigatório")
        PollStatus status,

        @NotNull(message = "Data de encerramento é obrigatório")
        @Future(message = "Data de encerramento deve estar no futuro")
        LocalDateTime endAt,

        @NotNull(message = "Opções de votação é obrigatório")
        @NotEmpty(message = "Deve ter pelo menos duas opções de voto")
        @Size(min = 2, message = "Deve ter pelo menos duas opções de voto")
        Set<CreateVotingOptionDTO> votingOptions
) {}
