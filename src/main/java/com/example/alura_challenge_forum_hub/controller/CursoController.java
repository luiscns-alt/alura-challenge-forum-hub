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

import com.example.alura_challenge_forum_hub.dto.CursoDTO;
import com.example.alura_challenge_forum_hub.dto.CursoResponseDTO;
import com.example.alura_challenge_forum_hub.model.Curso;
import com.example.alura_challenge_forum_hub.repository.CursoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@Validated
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Curso> cadastrar(@RequestBody @Valid CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());
        curso.setCategoria(cursoDTO.getCategoria());

        cursoRepository.save(curso);

        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDTO> cursosDTO = cursos.stream().map(curso -> {
            CursoResponseDTO dto = new CursoResponseDTO();
            dto.setId(curso.getId());
            dto.setNome(curso.getNome());
            dto.setCategoria(curso.getCategoria());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(cursosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> detalhar(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        CursoResponseDTO dto = new CursoResponseDTO();
        dto.setId(curso.getId());
        dto.setNome(curso.getNome());
        dto.setCategoria(curso.getCategoria());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody @Valid CursoDTO cursoDTO) {
        Optional<Curso> optionalCurso = cursoRepository.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = optionalCurso.get();
        curso.setNome(cursoDTO.getNome());
        curso.setCategoria(cursoDTO.getCategoria());

        cursoRepository.save(curso);

        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Curso> optionalCurso = cursoRepository.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
