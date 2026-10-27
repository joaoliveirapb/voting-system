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
@Table(name = "polls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PollStatus status;

    @Column(name = "total_votes")
    private int totalVotes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VotingOption> votingOptions = new HashSet<>();

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY)
    private Set<Vote> votes = new HashSet<>();

    public Poll(
            User user,
            String title,
            String question,
            PollStatus status,
            LocalDateTime endAt,
            Set<VotingOption> votingOptions
    ) {
        this.user = user;
        this.title = title;
        this.question = question;
        this.status = status;
        this.endAt = endAt;
        this.votingOptions.addAll(votingOptions);
        for (VotingOption option : votingOptions) {
            option.setPoll(this);
        }
    }

    @PrePersist
    protected void onCreate() {
        totalVotes = 0;
        createdAt = LocalDateTime.now();
    }
}
