package com.andre.project_finances.service;

import com.andre.project_finances.domain.dto.AccountDTO;
import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(AccountDTO accountDTO, User user) {
        Account account = new Account(accountDTO.name(), accountDTO.initialBalance(), user);
        this.saveAccount(account);
        return new AccountDTO(account.getName(), account.getInitialBalance());
    }

    public void saveAccount(Account account) {
        this.accountRepository.save(account);
    }
}
