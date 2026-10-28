package com.joaoliveirapb.voting_system.services;

import com.joaoliveirapb.voting_system.dtos.CreatePollRequestDTO;
import com.joaoliveirapb.voting_system.dtos.RegisterVoteRequestDTO;
import com.joaoliveirapb.voting_system.dtos.PollResponseDTO;
import com.joaoliveirapb.voting_system.entities.*;
import com.joaoliveirapb.voting_system.repositories.PollRepository;
import com.joaoliveirapb.voting_system.repositories.UserRepository;
import com.joaoliveirapb.voting_system.repositories.VoteRepository;
import com.joaoliveirapb.voting_system.repositories.VotingOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final VotingOptionRepository votingOptionRepository;
    private final VoteRepository voteRepository;

    public PollResponseDTO createPoll(CreatePollRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para criar enquete"));

        Set<VotingOption> votingOption = request.votingOptions().stream()
                .map(optionDTO -> new VotingOption(optionDTO.name())).collect(Collectors.toSet());

        Poll newPoll = new Poll(
                user,
                request.title(),
                request.question(),
                request.status(),
                request.endAt(),
                votingOption
        );

        Poll pollSaved = pollRepository.save(newPoll);

        return new PollResponseDTO(pollSaved);
    }

    public List<PollResponseDTO> getPolls() {
        return pollRepository.findAllWithVotingOptions().stream().map(PollResponseDTO::new).toList();
    }

    public Optional<PollResponseDTO> getPollById(UUID id) {
        return pollRepository.findByIdWithVotingOptions(id).map(PollResponseDTO::new);
    }

    @Transactional
    public void registerVote(UUID pollId, RegisterVoteRequestDTO request) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new RuntimeException("Enquete não encontrada"));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        VotingOption votingOption = votingOptionRepository.findById(request.votingOptionId())
                .orElseThrow(() -> new RuntimeException("Opção de voto não encontrada"));

        if (!poll.getStatus().equals(PollStatus.ACTIVE)) {
            throw new RuntimeException("Esta enquete não está aberta para votação");
        }

        if (voteRepository.existsByPollIdAndUserId(pollId, request.userId())) {
            throw new RuntimeException("Você já votou nesta enquete");
        }

        voteRepository.save(new Vote(poll, votingOption, user));
        votingOption.setVoteCount(votingOption.getVoteCount() + 1);
        poll.setTotalVotes(poll.getTotalVotes() + 1);
    }
}
