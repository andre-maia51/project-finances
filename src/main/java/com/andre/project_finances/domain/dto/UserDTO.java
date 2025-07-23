package com.andre.project_finances.domain.dto;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
