package com.example.alura_challenge_forum_hub.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.example.alura_challenge_forum_hub.dto.TopicoDTO;
import com.example.alura_challenge_forum_hub.dto.TopicoDetalheDTO;
import com.example.alura_challenge_forum_hub.dto.TopicoResponseDTO;
import com.example.alura_challenge_forum_hub.dto.TopicoUpdateDTO;
import com.example.alura_challenge_forum_hub.model.Curso;
import com.example.alura_challenge_forum_hub.model.Topico;
import com.example.alura_challenge_forum_hub.model.Usuario;
import com.example.alura_challenge_forum_hub.repository.CursoRepository;
import com.example.alura_challenge_forum_hub.repository.TopicoRepository;
import com.example.alura_challenge_forum_hub.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Topico> cadastrar(@RequestBody @Valid TopicoDTO topicoDTO) {
        if (topicoRepository.existsByTituloAndMensagem(topicoDTO.getTitulo(), topicoDTO.getMensagem())) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario autor = usuarioRepository.findById(topicoDTO.getAutorId()).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(topicoDTO.getCursoId()).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.getTitulo());
        topico.setMensagem(topicoDTO.getMensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus("ABERTO");

        topicoRepository.save(topico);

        return ResponseEntity.ok(topico);
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponseDTO>> listar(@PageableDefault(sort = "dataCriacao", direction = Sort.Direction.ASC, size = 10) Pageable paginacao) {
        Page<Topico> topicos = topicoRepository.findAll(paginacao);
        List<TopicoResponseDTO> topicosDTO = topicos.stream().map(topico -> {
            TopicoResponseDTO dto = new TopicoResponseDTO();
            dto.setId(topico.getId());
            dto.setTitulo(topico.getTitulo());
            dto.setMensagem(topico.getMensagem());
            dto.setDataCriacao(topico.getDataCriacao());
            dto.setStatus(topico.getStatus());
            dto.setAutorNome(topico.getAutor().getNome());
            dto.setCursoNome(topico.getCurso().getNome());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(topicosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalheDTO> detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));

        TopicoDetalheDTO dto = new TopicoDetalheDTO();
        dto.setId(topico.getId());
        dto.setTitulo(topico.getTitulo());
        dto.setMensagem(topico.getMensagem());
        dto.setDataCriacao(topico.getDataCriacao());
        dto.setStatus(topico.getStatus());
        dto.setAutorNome(topico.getAutor().getNome());
        dto.setCursoNome(topico.getCurso().getNome());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDTO topicoUpdateDTO) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optionalTopico.get();

        if (topicoRepository.existsByTituloAndMensagem(topicoUpdateDTO.getTitulo(), topicoUpdateDTO.getMensagem())) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario autor = usuarioRepository.findById(topicoUpdateDTO.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(topicoUpdateDTO.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        topico.setTitulo(topicoUpdateDTO.getTitulo());
        topico.setMensagem(topicoUpdateDTO.getMensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);

        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
