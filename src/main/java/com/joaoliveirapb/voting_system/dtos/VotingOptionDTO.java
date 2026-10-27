package com.joaoliveirapb.voting_system.dtos;

import java.util.UUID;

public record VotingOptionDTO(UUID id, String name, int voteCount) {
}
