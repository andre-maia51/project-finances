package com.andre.project_finances.controller;

import com.andre.project_finances.dto.TransactionDTO;
import com.andre.project_finances.dto.TransactionResponseDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
