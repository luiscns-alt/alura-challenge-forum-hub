package com.example.alura_challenge_forum_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.alura_challenge_forum_hub.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}