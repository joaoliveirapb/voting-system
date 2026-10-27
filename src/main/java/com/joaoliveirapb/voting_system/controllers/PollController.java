package com.joaoliveirapb.voting_system.controllers;

import com.joaoliveirapb.voting_system.dtos.CreatePollRequestDTO;
import com.joaoliveirapb.voting_system.dtos.PollResponseDTO;
import com.joaoliveirapb.voting_system.services.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;

    @PostMapping
    public ResponseEntity<PollResponseDTO> createPoll(@Valid @RequestBody CreatePollRequestDTO request) {
        try {
            PollResponseDTO response = pollService.createPoll(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("[PollController::createPoll]: Erro ao criar enquete: " + e.getMessage());
            throw new RuntimeException("Erro ao criar enquete: " + e.getMessage());
        }
    }
}
