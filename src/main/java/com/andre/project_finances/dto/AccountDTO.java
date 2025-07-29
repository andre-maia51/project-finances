package com.andre.project_finances.dto;

import java.math.BigDecimal;

public record AccountDTO(
        String name,
        BigDecimal initialBalance
) {
}
