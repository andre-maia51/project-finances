package com.andre.project_finances.domain.entities;

import com.andre.project_finances.dto.TransactionDTO;
import com.andre.project_finances.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    BigDecimal amount;
    LocalDateTime date;

    @Column(name = "transaction_type")
    TransactionType type;

    @ManyToOne @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne @JoinColumn(name = "category_id")
    Category category;

    public Transaction(TransactionDTO transactionDTO, Account account, Category category) {
        this.description = transactionDTO.description();
        this.amount = transactionDTO.amount();
        this.date = LocalDateTime.now();
        this.type = transactionDTO.type();
        this.account = account;
        this.category = category;
    }
}
