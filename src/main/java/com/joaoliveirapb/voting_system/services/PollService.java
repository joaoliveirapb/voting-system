package com.joaoliveirapb.voting_system.services;

import com.joaoliveirapb.voting_system.dtos.*;
import com.joaoliveirapb.voting_system.entities.*;
import com.joaoliveirapb.voting_system.repositories.PollRepository;
import com.joaoliveirapb.voting_system.repositories.UserRepository;
import com.joaoliveirapb.voting_system.repositories.VoteRepository;
import com.joaoliveirapb.voting_system.repositories.VotingOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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

    @Transactional
    public void endPoll(UUID pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Enquete não encontrada para encerramento"));

        if (poll.getStatus().equals(PollStatus.COMPLETED)) {
            return;
        }

        poll.setStatus(PollStatus.COMPLETED);
    }

    public GetPollResultsResponseDTO getPollResults(UUID pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Enquete não encontrada para ver resultado"));

        VotingOption winner = poll.getVotingOptions().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vencedor não encontrado"));

        return new GetPollResultsResponseDTO(
                poll.getId(),
                poll.getQuestion(),
                poll.getTotalVotes(),
                winner.getName(),
                poll.getVotingOptions().stream()
                        .map(option -> new VotingOptionForPollResultsDTO(
                                option.getId(),
                                option.getName(),
                                option.getVoteCount(),
                                calculatePercentage(option.getVoteCount(), poll.getTotalVotes())
                        ))
                        .toList()
        );
    }

    private double calculatePercentage(int value, int totalValue) {
        if (value == 0 || totalValue == 0) {
            return 0;
        }

        double percentage = ((double) value / totalValue) * 100;

        return BigDecimal.valueOf(percentage).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
