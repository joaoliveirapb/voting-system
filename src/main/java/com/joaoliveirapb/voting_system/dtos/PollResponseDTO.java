package com.joaoliveirapb.voting_system.dtos;

import com.joaoliveirapb.voting_system.entities.Poll;
import com.joaoliveirapb.voting_system.entities.PollStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PollResponseDTO(
        UUID id,
        String title,
        String question,
        PollStatus pollStatus,
        int totalVotes,
        LocalDateTime createdAt,
        LocalDateTime endAt,
        List<VotingOptionDTO> votingOptions
) {
    public PollResponseDTO(Poll pollEntity) {
        this(
                pollEntity.getId(),
                pollEntity.getTitle(),
                pollEntity.getQuestion(),
                pollEntity.getStatus(),
                pollEntity.getTotalVotes(),
                pollEntity.getCreatedAt(),
                pollEntity.getEndAt(),
                pollEntity.getVotingOptions().stream()
                        .map(option -> new VotingOptionDTO(option.getId(), option.getName(), option.getVoteCount()))
                        .toList()
        );
    }
}
