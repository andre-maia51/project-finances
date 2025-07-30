package com.andre.project_finances.service;

import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.Transaction;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.domain.enums.TransactionType;
import com.andre.project_finances.dto.TransactionDTO;
import com.andre.project_finances.dto.TransactionResponseDTO;
import com.andre.project_finances.infra.excepctions.InsufficientBalanceException;
import com.andre.project_finances.infra.excepctions.ResourceNotFoundException;
import com.andre.project_finances.infra.excepctions.UnauthorizedOperationException;
import com.andre.project_finances.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    @Test
    @DisplayName("Should create an INCOME transaction with success")
    void createTransactionIncome() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");

        Category category = new Category(2L, "Alimentação", "Descrição", owner);
        Account account = new Account(3L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(10),
                TransactionType.INCOME,
                3L,
                2L
        );

        when(accountService.findAccount(3L)).thenReturn(Optional.of(account));
        when(categoryService.findCategory(2L)).thenReturn(Optional.of(category));

        TransactionResponseDTO response = transactionService.createTransaction(transactionDTO, owner);

        assertEquals(new BigDecimal(20), account.getInitialBalance());

        verify(transactionRepository, times(1)).save(any(Transaction.class));

        assertNotNull(response);
        assertEquals("Transação", response.description());
        assertEquals("Poupança", response.accountName());
    }

    @Test
    @DisplayName("Should create an EXPENSE transaction with success")
    void createTransactionExpense() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");

        Category category = new Category(2L, "Alimentação", "Descrição", owner);
        Account account = new Account(3L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(10),
                TransactionType.EXPENSE,
                3L,
                2L
        );

        when(accountService.findAccount(3L)).thenReturn(Optional.of(account));
        when(categoryService.findCategory(2L)).thenReturn(Optional.of(category));

        TransactionResponseDTO response = transactionService.createTransaction(transactionDTO, owner);

        assertEquals(new BigDecimal(0), account.getInitialBalance());

        verify(transactionRepository, times(1)).save(any(Transaction.class));

        assertNotNull(response);
        assertEquals("Transação", response.description());
        assertEquals("Poupança", response.accountName());
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException")
    void createTransactionInsufficientBalance() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");

        Category category = new Category(2L, "Alimentação", "Descrição", owner);
        Account account = new Account(3L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(1000),
                TransactionType.EXPENSE,
                3L,
                2L
        );

        when(accountService.findAccount(3L)).thenReturn(Optional.of(account));
        when(categoryService.findCategory(2L)).thenReturn(Optional.of(category));

        assertThrows(InsufficientBalanceException.class, () ->
                transactionService.createTransaction(transactionDTO, owner)
        );

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException for Account")
    void createTransactionNotFoundAccount() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");

        Category category = new Category(2L, "Alimentação", "Descrição", owner);
        Account account = new Account(3L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(1000),
                TransactionType.EXPENSE,
                3L,
                2L
        );

        when(categoryService.findCategory(2L)).thenReturn(Optional.of(category));
        when(accountService.findAccount(3L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                transactionService.createTransaction(transactionDTO, owner)
        );

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException for Category")
    void createTransactionNotFoundCategory() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");

        Category category = new Category(2L, "Alimentação", "Descrição", owner);
        Account account = new Account(3L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(1000),
                TransactionType.EXPENSE,
                3L,
                2L
        );

        when(categoryService.findCategory(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                transactionService.createTransaction(transactionDTO, owner)
        );

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw UnauthorizedOperationException for Account")
    void createTransactionUnauthorizedOperationAccount() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");
        User anotherOwner = new User(2L, "Maria", "Souza", "maria@email.com", "4321");

        Category category = new Category(3L, "Alimentação", "Descrição", owner);
        Account account = new Account(4L, "Poupança", new BigDecimal(10), anotherOwner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(1),
                TransactionType.EXPENSE,
                4L,
                3L
        );

        when(accountService.findAccount(4L)).thenReturn(Optional.of(account));
        when(categoryService.findCategory(3L)).thenReturn(Optional.of(category));

        assertThrows(UnauthorizedOperationException.class, () ->
                transactionService.createTransaction(transactionDTO, owner)
        );

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw UnauthorizedOperationException for Category")
    void createTransactionUnauthorizedOperationCategory() {
        User owner = new User(1L, "João", "Silva", "joao@email.com", "1234");
        User anotherOwner = new User(2L, "Maria", "Souza", "maria@email.com", "4321");

        Category category = new Category(3L, "Alimentação", "Descrição", anotherOwner);
        Account account = new Account(4L, "Poupança", new BigDecimal(10), owner);

        TransactionDTO transactionDTO = new TransactionDTO(
                "Transação",
                new BigDecimal(1),
                TransactionType.EXPENSE,
                4L,
                3L
        );

        when(accountService.findAccount(4L)).thenReturn(Optional.of(account));
        when(categoryService.findCategory(3L)).thenReturn(Optional.of(category));

        assertThrows(UnauthorizedOperationException.class, () ->
                transactionService.createTransaction(transactionDTO, owner)
        );

        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}