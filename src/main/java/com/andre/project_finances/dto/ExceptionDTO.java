package com.andre.project_finances.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(
        String message,
        HttpStatus statusCode
) {
}
