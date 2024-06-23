package com.example.alura_challenge_forum_hub.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaResponseDTO {
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String autorNome;
    private Long topicoId;
    private Boolean solucao;
}
