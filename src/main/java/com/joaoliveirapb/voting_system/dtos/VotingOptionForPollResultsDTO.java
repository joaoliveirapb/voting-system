package com.joaoliveirapb.voting_system.dtos;

import java.util.UUID;

public record VotingOptionForPollResultsDTO(UUID id, String name, int voteCount, double percentage) {
}
