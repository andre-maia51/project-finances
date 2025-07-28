package com.andre.project_finances.domain.dto;

import com.andre.project_finances.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        String description,
        BigDecimal amount,
        TransactionType type,
        Long account,
        Long category
) {
}
