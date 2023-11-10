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

import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;
import com.facens.devopsproject.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag; 


@RestController
@RequestMapping(value = "/aluno")
@Tag(name = "aluno", description = "operations about aluno")
public class AlunoController {

    private AlunoService alunoService;

    @Autowired
    public void AlunoService(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    @Operation(
        tags = "aluno",
        description = "Get all Alunos",
        responses = {
            @ApiResponse(description = "successful operation", responseCode = "200")
        }
    )
    public ResponseEntity<AlunoResponse> getAlunos(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(alunoService.getAllAlunos(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
        tags = "aluno",
        description = "Find aluno by id",
        responses = {
            @ApiResponse(description = "successful operation", responseCode = "200"),
            @ApiResponse(description = "aluno not found with this id", responseCode = "404", content = @Content),
            @ApiResponse(description = "invalid ID supplied", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<AlunoDTO> alunoDetail(@PathVariable int id) {
        return ResponseEntity.ok(alunoService.getAlunoById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        tags = "aluno",
        description = "Add a new aluno",
        responses = {
            @ApiResponse(description = "successful operation, created", responseCode = "201"),
            @ApiResponse(description = "invalid input, bad request error", responseCode = "400", content = @Content)
        })
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoDTO alunoDTO) {
        return new ResponseEntity<>(alunoService.createAluno(alunoDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(
        tags = "aluno",
        description = "Update an existing aluno",
        responses = {
            @ApiResponse(description = "successful operation, updated", responseCode = "200"),
            @ApiResponse(description = "invalid input, bad request error", responseCode = "400", content = @Content),
            @ApiResponse(description = "aluno not found with this id", responseCode = "404", content = @Content),
        })
    public ResponseEntity<AlunoDTO> updateAluno(@RequestBody AlunoDTO alunoDTO, @PathVariable("id") int alunoId) {
        AlunoDTO response = alunoService.updateAlunoById(alunoDTO, alunoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
        tags = "aluno",
        description = "Deletes an aluno",
        responses = {
            @ApiResponse(description = "successful operation, deleted", responseCode = "204", content = @Content),
            @ApiResponse(description = "invalid ID supplied", responseCode = "400", content = @Content),
            @ApiResponse(description = "aluno not found with this id", responseCode = "404", content = @Content),
        })
    public ResponseEntity<String> deleteAluno(@PathVariable("id") int cursoId) {
        alunoService.deleteAlunoById(cursoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
