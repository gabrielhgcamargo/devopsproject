package com.facens.devopsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.facens.devopsproject.models.Curso;
import com.facens.devopsproject.repository.CursoRepository;

@RestController
@RequestMapping(value = "/curso")
public class CursoController {
    
    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    public List<Curso> listaCursos(){
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Curso getCursoById(@PathVariable(value = "id") Integer id){
        return cursoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Curso não encontrado com o ID informado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso createCurso(@RequestBody Curso curso){
    return cursoRepository.save(curso);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaCursoById(@PathVariable(value = "id") Integer id){
        cursoRepository.findById(id).map(curso -> {
           cursoRepository.deleteById(id);
           return curso;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                       "Curso não encontrado com o ID informado."));
   }

    @PutMapping("/{id}")
    public Curso atualizaCursoById(@PathVariable(value = "id") Integer id, @RequestBody Curso curso){
         return cursoRepository.findById(id).map(curso1 -> {
            curso.setId(curso1.getId());
            cursoRepository.save(curso);
            return curso1;
         }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Curso não encontrado com o ID informado."));
    }
}
