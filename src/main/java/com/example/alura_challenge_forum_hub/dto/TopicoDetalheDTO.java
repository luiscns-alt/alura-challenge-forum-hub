package com.example.alura_challenge_forum_hub.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicoDetalheDTO {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;
    private String autorNome;
    private String cursoNome;
}
