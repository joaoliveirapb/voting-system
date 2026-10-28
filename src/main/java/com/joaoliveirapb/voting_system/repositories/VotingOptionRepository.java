package com.joaoliveirapb.voting_system.repositories;

import com.joaoliveirapb.voting_system.entities.VotingOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotingOptionRepository extends JpaRepository<VotingOption, UUID> {
}
