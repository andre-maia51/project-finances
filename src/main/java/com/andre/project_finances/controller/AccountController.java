package com.andre.project_finances.controller;

import com.andre.project_finances.dto.AccountDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO, Authentication auth) {
        User owner = (User) auth.getPrincipal();
        AccountDTO response = this.accountService.createAccount(accountDTO, owner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
