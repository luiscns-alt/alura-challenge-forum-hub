package com.example.alura_challenge_forum_hub.controller;

import com.example.alura_challenge_forum_hub.dto.LoginDTO;
import com.example.alura_challenge_forum_hub.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenService.gerarToken(loginDTO.getEmail());
            return ResponseEntity.ok("Login successful. Token: " + token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
}
