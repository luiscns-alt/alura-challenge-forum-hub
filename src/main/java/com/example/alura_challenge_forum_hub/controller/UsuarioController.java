package com.example.alura_challenge_forum_hub.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alura_challenge_forum_hub.dto.UsuarioDTO;
import com.example.alura_challenge_forum_hub.dto.UsuarioResponseDTO;
import com.example.alura_challenge_forum_hub.model.Perfil;
import com.example.alura_challenge_forum_hub.model.Usuario;
import com.example.alura_challenge_forum_hub.repository.PerfilRepository;
import com.example.alura_challenge_forum_hub.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Perfil perfil = perfilRepository.findById(usuarioDTO.getPerfilId())
                    .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            usuario.setPerfil(perfil);

            usuarioRepository.save(usuario);

            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> usuariosDTO = usuarios.stream().map(usuario -> {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setPerfilNome(usuario.getPerfil().getNome());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> detalhar(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPerfilNome(usuario.getPerfil().getNome());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = optionalUsuario.get();

        Perfil perfil = perfilRepository.findById(usuarioDTO.getPerfilId())
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setPerfil(perfil);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
