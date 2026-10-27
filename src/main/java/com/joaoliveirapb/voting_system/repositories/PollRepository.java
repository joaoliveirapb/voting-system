package com.joaoliveirapb.voting_system.repositories;

import com.joaoliveirapb.voting_system.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll, UUID> {
}
