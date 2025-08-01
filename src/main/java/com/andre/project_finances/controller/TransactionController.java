package com.andre.project_finances.controller;

import com.andre.project_finances.dto.TransactionDTO;
import com.andre.project_finances.dto.TransactionResponseDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionDTO transactionDTO, Authentication auth) {
        User owner = (User) auth.getPrincipal();
        TransactionResponseDTO response = this.transactionService.createTransaction(transactionDTO, owner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Authentication auth
    ) {
        User owner = (User) auth.getPrincipal();
        List<TransactionResponseDTO> response = this.transactionService.listTransactions(owner, year, month);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> changeTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        TransactionResponseDTO response = this.transactionService.changeTransaction(id, transactionDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
