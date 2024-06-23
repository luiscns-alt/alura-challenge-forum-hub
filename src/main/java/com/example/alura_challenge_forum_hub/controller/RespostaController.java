package com.example.alura_challenge_forum_hub.controller;

import java.time.LocalDateTime;
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

import com.example.alura_challenge_forum_hub.dto.RespostaDTO;
import com.example.alura_challenge_forum_hub.dto.RespostaResponseDTO;
import com.example.alura_challenge_forum_hub.model.Resposta;
import com.example.alura_challenge_forum_hub.model.Topico;
import com.example.alura_challenge_forum_hub.model.Usuario;
import com.example.alura_challenge_forum_hub.repository.RespostaRepository;
import com.example.alura_challenge_forum_hub.repository.TopicoRepository;
import com.example.alura_challenge_forum_hub.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/respostas")
@Validated
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Resposta> cadastrar(@RequestBody @Valid RespostaDTO respostaDTO) {
        Topico topico = topicoRepository.findById(respostaDTO.getTopicoId())
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        Usuario autor = usuarioRepository.findById(respostaDTO.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

        Resposta resposta = new Resposta();
        resposta.setMensagem(respostaDTO.getMensagem());
        resposta.setTopico(topico);
        resposta.setAutor(autor);
        resposta.setDataCriacao(LocalDateTime.now());
        resposta.setSolucao(respostaDTO.getSolucao());

        respostaRepository.save(resposta);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public ResponseEntity<List<RespostaResponseDTO>> listar() {
        List<Resposta> respostas = respostaRepository.findAll();
        List<RespostaResponseDTO> respostasDTO = respostas.stream().map(resposta -> {
            RespostaResponseDTO dto = new RespostaResponseDTO();
            dto.setId(resposta.getId());
            dto.setMensagem(resposta.getMensagem());
            dto.setDataCriacao(resposta.getDataCriacao());
            dto.setAutorNome(resposta.getAutor().getNome());
            dto.setTopicoId(resposta.getTopico().getId());
            dto.setSolucao(resposta.getSolucao());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(respostasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> detalhar(@PathVariable Long id) {
        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));

        RespostaResponseDTO dto = new RespostaResponseDTO();
        dto.setId(resposta.getId());
        dto.setMensagem(resposta.getMensagem());
        dto.setDataCriacao(resposta.getDataCriacao());
        dto.setAutorNome(resposta.getAutor().getNome());
        dto.setTopicoId(resposta.getTopico().getId());
        dto.setSolucao(resposta.getSolucao());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> atualizar(@PathVariable Long id, @RequestBody @Valid RespostaDTO respostaDTO) {
        Optional<Resposta> optionalResposta = respostaRepository.findById(id);
        if (!optionalResposta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Resposta resposta = optionalResposta.get();

        Topico topico = topicoRepository.findById(respostaDTO.getTopicoId())
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        Usuario autor = usuarioRepository.findById(respostaDTO.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

        resposta.setMensagem(respostaDTO.getMensagem());
        resposta.setTopico(topico);
        resposta.setAutor(autor);
        resposta.setSolucao(respostaDTO.getSolucao());

        respostaRepository.save(resposta);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Resposta> optionalResposta = respostaRepository.findById(id);
        if (!optionalResposta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        respostaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
