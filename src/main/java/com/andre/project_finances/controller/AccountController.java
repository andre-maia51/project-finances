package com.andre.project_finances.controller;

import com.andre.project_finances.dto.AccountDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccountsByUser(Authentication auth) {
        User owner = (User) auth.getPrincipal();
        List<AccountDTO> response = this.accountService.getAccountsByUser(owner);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountByUserId(@PathVariable Long id) {
        AccountDTO response = this.accountService.getAccountByUserId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> changeAccountName(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        AccountDTO response = this.accountService.changeAccountName(id, accountDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        this.accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
