package com.andre.project_finances.controller;

import com.andre.project_finances.dto.UserDTO;
import com.andre.project_finances.dto.UserResponseDTO;
import com.andre.project_finances.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        UserResponseDTO userResponseDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }
}
