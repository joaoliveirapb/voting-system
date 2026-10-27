package com.joaoliveirapb.voting_system.services;

import com.joaoliveirapb.voting_system.dtos.CreatePollRequestDTO;
import com.joaoliveirapb.voting_system.dtos.PollResponseDTO;
import com.joaoliveirapb.voting_system.entities.Poll;
import com.joaoliveirapb.voting_system.entities.User;
import com.joaoliveirapb.voting_system.entities.VotingOption;
import com.joaoliveirapb.voting_system.repositories.PollRepository;
import com.joaoliveirapb.voting_system.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final UserRepository userRepository;

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
}
