package com.joaoliveirapb.voting_system.dtos;

import java.util.List;
import java.util.UUID;

public record GetPollResultsResponseDTO(
        UUID id,
        String question,
        int totalVotes,
        String winner,
        List<VotingOptionForPollResultsDTO> votingOptions
) {}
