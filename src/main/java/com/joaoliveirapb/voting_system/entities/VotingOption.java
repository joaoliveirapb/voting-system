package com.joaoliveirapb.voting_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "voting_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotingOption {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @Column(nullable = false)
    private String name;

    @Column(name = "vote_count", nullable = false)
    private int voteCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "votingOption", fetch = FetchType.LAZY)
    private Set<Vote> votes = new HashSet<>();

    public VotingOption(String name) {
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        voteCount = 0;
        createdAt = LocalDateTime.now();
    }
}
