package com.andre.project_finances.service;

import com.andre.project_finances.dto.TransactionDTO;
import com.andre.project_finances.dto.TransactionResponseDTO;
import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.Transaction;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.domain.enums.TransactionType;
import com.andre.project_finances.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountService accountService, CategoryService categoryService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionDTO transactionDTO, User user) {
        Category category = this.getCategory(transactionDTO.category());
        Account account = this.getAccount(transactionDTO.account());
        this.verifyUser(account, category, user);

        if(transactionDTO.type() == TransactionType.INCOME) {
            account.setInitialBalance(account.getInitialBalance().add(transactionDTO.amount()));
        }

        if(transactionDTO.type() == TransactionType.EXPENSE) {
            account.setInitialBalance(account.getInitialBalance().subtract(transactionDTO.amount()));
        }

        Transaction transaction = new Transaction(transactionDTO, account, category);

        this.transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                account.getName(),
                category.getName());
    }

    public Category getCategory(Long id) {
        return this.categoryService.findCategory(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    public Account getAccount(Long id) {
        return this.accountService.findAccount(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }

    public void verifyUser(Account account, Category category, User user) {
        if(!account.getUser().equals(user)) {
            throw new SecurityException("A conta informada não pertence ao usuário autenticado");
        }

        if(!category.getUser().equals(user)) {
            throw new SecurityException("A categoria informada não pertence ao usuário autenticado");
        }
    }


}
