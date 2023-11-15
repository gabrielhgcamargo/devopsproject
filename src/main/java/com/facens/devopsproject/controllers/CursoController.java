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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/curso")
@Tag(name = "curso", description = "operations about curso")
public class CursoController {

    private CursoService cursoService;

    @Autowired
    public void CursoService(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(
        tags = "curso",
        description = "Get all Cursos",
        responses = {
            @ApiResponse(description = "successful operation", responseCode = "200")
        }
    )
    public ResponseEntity<CursoResponse> getCursos(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(cursoService.getAllCursos(pageNo, pageSize), HttpStatus.OK);
    }

    // Desenvolvida por: Gabriel Henrique
    @GetMapping("/{id}")
    @Operation(
        tags = "curso",
        description = "Find curso by id",
        responses = {
            @ApiResponse(description = "successful operation", responseCode = "200"),
            @ApiResponse(description = "curso not found with this id", responseCode = "404", content = @Content),
            @ApiResponse(description = "invalid ID supplied", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<CursoDTO> cursoDetail(@PathVariable int id) {
        return ResponseEntity.ok(cursoService.getCursoById(id));

    }

    // Desenvolvida por: Vitor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        tags = "curso",
        description = "Add a new curso",
        responses = {
            @ApiResponse(description = "successful operation, created", responseCode = "201"),
            @ApiResponse(description = "invalid input, bad request error", responseCode = "400", content = @Content)
        })
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO) {
        return new ResponseEntity<>(cursoService.createCurso(cursoDTO), HttpStatus.CREATED);
    }

    // Desenvolvida por: Luis    
    @PutMapping("{id}")
    @Operation(
        tags = "curso",
        description = "Update an existing curso",
        responses = {
            @ApiResponse(description = "successful operation, updated", responseCode = "200"),
            @ApiResponse(description = "invalid input, bad request error", responseCode = "400", content = @Content),
            @ApiResponse(description = "curso not found with this id", responseCode = "404", content = @Content),
        })
    public ResponseEntity<CursoDTO> updateCurso(@RequestBody CursoDTO cursoDTO, @PathVariable("id") int cursoId) {
        CursoDTO response = cursoService.updateCursoById(cursoDTO, cursoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
        tags = "curso",
        description = "Deletes a curso",
        responses = {
            @ApiResponse(description = "successful operation, deleted", responseCode = "204", content = @Content),
            @ApiResponse(description = "invalid ID supplied", responseCode = "400", content = @Content),
            @ApiResponse(description = "curso not found with this id", responseCode = "404", content = @Content),
        })
    public ResponseEntity<String> deleteCurso(@PathVariable("id") int cursoId) {
        cursoService.deleteCursoById(cursoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}