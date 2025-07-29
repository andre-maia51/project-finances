package com.andre.project_finances.dto;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
