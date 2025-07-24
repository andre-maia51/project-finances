package com.andre.project_finances.domain.dto;

import java.math.BigDecimal;

public record AccountDTO(
        String name,
        BigDecimal initialBalance
) {
}
