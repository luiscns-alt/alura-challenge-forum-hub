package com.example.alura_challenge_forum_hub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "topico")
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "autor")
    private Usuario autor;
}
