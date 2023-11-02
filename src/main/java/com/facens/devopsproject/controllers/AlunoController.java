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

import com.facens.devopsproject.models.Aluno;
import com.facens.devopsproject.repository.AlunoRepository;

@RestController
@RequestMapping(value = "/aluno")
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping
    public List<Aluno> listaAlunos(){
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aluno getAlunoById(@PathVariable(value = "id") Integer id){
        return alunoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Aluno não encontrado com o ID informado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno createAluno(@RequestBody Aluno aluno){
    return alunoRepository.save(aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaAlunoById(@PathVariable(value = "id") Integer id){
        alunoRepository.findById(id).map(aluno -> {
           alunoRepository.deleteById(id);
           return aluno;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                       "Aluno não encontrado com o ID informado."));
   }

    @PutMapping("/{id}")
    public Aluno atualizaAlunoById(@PathVariable(value = "id") Integer id, @RequestBody Aluno aluno){
         return alunoRepository.findById(id).map(aluno1 -> {
            aluno.setId(aluno1.getId());
            alunoRepository.save(aluno);
            return aluno1;
         }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aluno não encontrado com o ID informado."));
    }
    
}
