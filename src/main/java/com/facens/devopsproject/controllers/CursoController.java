package com.facens.devopsproject.controllers;

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
import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;
import com.facens.devopsproject.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private CursoService cursoService;

    @Autowired
    public void CursoService(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<CursoResponse> getCursos(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(cursoService.getAllCursos(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> cursoDetail(@PathVariable int id) {
        return ResponseEntity.ok(cursoService.getCursoById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO) {
        return new ResponseEntity<>(cursoService.createCurso(cursoDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CursoDTO> updateCurso(@RequestBody CursoDTO cursoDTO, @PathVariable("id") int cursoId) {
        CursoDTO response = cursoService.updateCursoById(cursoDTO, cursoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int cursoId) {
        cursoService.deleteCursoById(cursoId);
        return new ResponseEntity<>("Curso delete", HttpStatus.OK);
    }

}