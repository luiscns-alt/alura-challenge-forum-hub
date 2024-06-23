package com.example.alura_challenge_forum_hub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicoUpdateDTO {
    @NotEmpty
    private String titulo;

    @NotEmpty
    private String mensagem;

    @NotNull
    private Long autorId;

    @NotNull
    private Long cursoId;
}
