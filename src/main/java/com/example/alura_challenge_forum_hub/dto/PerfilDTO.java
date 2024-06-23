package com.example.alura_challenge_forum_hub.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilDTO {
    @NotEmpty
    private String nome;
}
