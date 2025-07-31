package com.andre.project_finances.service;

import com.andre.project_finances.dto.AccountDTO;
import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Account> findAccount(Long id) {
        return this.accountRepository.findById(id);
    }

    public List<AccountDTO> getAccountsByUser(User user) {
        return this.accountRepository.findAccountByUser(user)
                .stream()
                .map(account -> new AccountDTO(account.getName(), account.getInitialBalance()))
                .toList();
    }
}
