package com.example.alura_challenge_forum_hub.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoDTO {
    @NotEmpty
    private String nome;

    @NotEmpty
    private String categoria;
}
