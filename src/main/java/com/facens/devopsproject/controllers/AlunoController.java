package com.facens.devopsproject.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;
import com.facens.devopsproject.service.AlunoService;


@RestController
@RequestMapping(value = "/aluno")
public class AlunoController {

    private AlunoService alunoService;

    @Autowired
    public void AlunoService(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public ResponseEntity<AlunoResponse> getCursos(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(alunoService.getAllAlunos(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> cursoDetail(@PathVariable int id) {
        return ResponseEntity.ok(alunoService.getAlunoById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoDTO alunoDTO) {
        return new ResponseEntity<>(alunoService.createAluno(alunoDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@RequestBody AlunoDTO alunoDTO, @PathVariable("id") int alunoId) {
        AlunoDTO response = alunoService.updateAlunoById(alunoDTO, alunoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int cursoId) {
        alunoService.deleteAlunoById(cursoId);
        return new ResponseEntity<>("Curso delete", HttpStatus.OK);
    }
    
}
