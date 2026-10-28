package com.joaoliveirapb.voting_system.repositories;

import com.joaoliveirapb.voting_system.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
    boolean existsByPollIdAndUserId(UUID pollId, UUID userId);
}
