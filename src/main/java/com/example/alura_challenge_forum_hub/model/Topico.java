package com.example.alura_challenge_forum_hub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;

    @ManyToOne
    @JoinColumn(name = "autor")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso")
    private Curso curso;

    private Integer respostas;
}
