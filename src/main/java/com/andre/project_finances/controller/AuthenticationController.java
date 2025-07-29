package com.andre.project_finances.controller;

import com.andre.project_finances.dto.LoginDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.infra.security.TokenDataDTO;
import com.andre.project_finances.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TokenDataDTO> login(@RequestBody LoginDTO loginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        var authenticator = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.tokenGenerator((User) authenticator.getPrincipal());
        return new ResponseEntity<>(new TokenDataDTO(tokenJWT), HttpStatus.OK);
    }
}
