package com.example.alura_challenge_forum_hub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String perfilNome;
}
