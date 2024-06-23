package com.example.alura_challenge_forum_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.alura_challenge_forum_hub.model.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}
