package com.joaoliveirapb.voting_system.controllers;

import com.joaoliveirapb.voting_system.dtos.CreatePollRequestDTO;
import com.joaoliveirapb.voting_system.dtos.PollResponseDTO;
import com.joaoliveirapb.voting_system.services.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<PollResponseDTO>> getPolls() {
        try {
            List<PollResponseDTO> response = pollService.getPolls();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("[PollController::getPolls]: Erro ao listar enquetes: " + e.getMessage());
            throw new RuntimeException("Erro ao listar enquetes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponseDTO> getPoll(@PathVariable UUID id) {
        try {
            return pollService.getPollById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.out.println("[PollController::createPoll]: Erro ao buscar enquete: " + e.getMessage());
            throw new RuntimeException("[PollController::createPoll]: Erro ao buscar enquete: " + e.getMessage());
        }
    }
}
