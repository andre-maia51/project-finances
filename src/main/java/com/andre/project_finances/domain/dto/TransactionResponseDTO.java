package com.andre.project_finances.domain.dto;

import com.andre.project_finances.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String description,
        BigDecimal amount,
        LocalDateTime date,
        TransactionType type,
        String accountName,
        String categoryName
) {
}
