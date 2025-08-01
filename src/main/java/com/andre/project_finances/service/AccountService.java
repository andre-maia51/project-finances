package com.andre.project_finances.service;

import com.andre.project_finances.dto.AccountDTO;
import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.infra.excepctions.BusinessRuleConflict;
import com.andre.project_finances.infra.excepctions.ResourceNotFoundException;
import com.andre.project_finances.repository.AccountRepository;
import com.andre.project_finances.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    public AccountDTO getAccountByUserId(Long id) {
        Account account = this.getAccountFromRepository(id);
        return new AccountDTO(account.getName(), account.getInitialBalance());
    }

    @Transactional
    public AccountDTO changeAccountName(Long id, AccountDTO accountDTO) {
        Account account = this.getAccountFromRepository(id);
        account.setName(accountDTO.name());
        this.accountRepository.save(account);
        return new AccountDTO(account.getName(), account.getInitialBalance());
    }

    public Account getAccountFromRepository(Long id) {
        return this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account account = this.getAccountFromRepository(id);
        if(this.transactionRepository.existsByAccount(account)) {
            throw new BusinessRuleConflict("Não é possível apagar uma conta com transações associadas");
        } else {
            this.accountRepository.delete(account);
        }
    }
}
