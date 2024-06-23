package com.example.alura_challenge_forum_hub.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alura_challenge_forum_hub.dto.PerfilDTO;
import com.example.alura_challenge_forum_hub.dto.PerfilResponseDTO;
import com.example.alura_challenge_forum_hub.model.Perfil;
import com.example.alura_challenge_forum_hub.repository.PerfilRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfis")
@Validated
public class PerfilController {
    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<Perfil> cadastrar(@RequestBody @Valid PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();
        perfil.setNome(perfilDTO.getNome());

        perfilRepository.save(perfil);

        return ResponseEntity.ok(perfil);
    }

    @GetMapping
    public ResponseEntity<List<PerfilResponseDTO>> listar() {
        List<Perfil> perfis = perfilRepository.findAll();
        List<PerfilResponseDTO> perfisDTO = perfis.stream().map(perfil -> {
            PerfilResponseDTO dto = new PerfilResponseDTO();
            dto.setId(perfil.getId());
            dto.setNome(perfil.getNome());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(perfisDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> detalhar(@PathVariable Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

        PerfilResponseDTO dto = new PerfilResponseDTO();
        dto.setId(perfil.getId());
        dto.setNome(perfil.getNome());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @RequestBody @Valid PerfilDTO perfilDTO) {
        Optional<Perfil> optionalPerfil = perfilRepository.findById(id);
        if (!optionalPerfil.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Perfil perfil = optionalPerfil.get();
        perfil.setNome(perfilDTO.getNome());

        perfilRepository.save(perfil);

        return ResponseEntity.ok(perfil);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Perfil> optionalPerfil = perfilRepository.findById(id);
        if (!optionalPerfil.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        perfilRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
