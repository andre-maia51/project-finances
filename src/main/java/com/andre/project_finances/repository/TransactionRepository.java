package com.andre.project_finances.repository;

import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.Transaction;
import com.andre.project_finances.domain.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM transaction t " +
            "WHERE t.account.user = :user " +
            "AND EXTRACT(YEAR FROM t.date) = :year " +
            "AND EXTRACT(MONTH FROM t.date) = :month")
    List<Transaction> findAllByUserAndMonthAndYear(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month
    );

    List<Transaction> findTransactionsByAccountUser(User user);

    Boolean existsByAccount(Account account);

    Boolean existsByCategory(Category category);
}
