package com.facens.devopsproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facens.devopsproject.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer>{
    
    Optional<Curso> findById(Integer id);

}
