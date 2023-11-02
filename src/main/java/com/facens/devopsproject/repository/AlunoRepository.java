package com.facens.devopsproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facens.devopsproject.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
    
    Optional<Aluno> findById(Integer id);

}
