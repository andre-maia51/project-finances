package com.andre.project_finances.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "account")
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    BigDecimal initialBalance;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;

    public Account(String name, BigDecimal initialBalance, User user) {
        this.name = name;
        this.initialBalance = initialBalance;
        this.user = user;
    }
}
