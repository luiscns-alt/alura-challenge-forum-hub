package com.example.alura_challenge_forum_hub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaDTO {
    @NotEmpty
    private String mensagem;

    @NotNull
    private Long topicoId;

    @NotNull
    private Long autorId;

    private Boolean solucao;
}
