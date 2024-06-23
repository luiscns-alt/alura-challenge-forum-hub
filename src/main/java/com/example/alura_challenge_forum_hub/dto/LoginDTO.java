package com.example.alura_challenge_forum_hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String senha;
}
