package com.facens.devopsproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;
import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;
import com.facens.devopsproject.exception.CursoNotFoundException;
import com.facens.devopsproject.models.Aluno;
import com.facens.devopsproject.models.Curso;
import com.facens.devopsproject.repository.AlunoRepository;
import com.facens.devopsproject.repository.CursoRepository;
import com.facens.devopsproject.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService{
    
    private AlunoRepository alunoRepository;

    @Autowired
    public void AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public AlunoDTO createAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.getNome());
        aluno.setCpf(alunoDTO.getCpf());

        Aluno newAluno = alunoRepository.save(aluno);

        AlunoDTO alunoResponse = new AlunoDTO();
        alunoResponse.setId(newAluno.getId());
        alunoResponse.setNome(newAluno.getNome());
        alunoResponse.setCpf(newAluno.getCpf());
        return alunoResponse;
    }

    @Override
    public AlunoResponse getAllAlunos(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Aluno> alunos = alunoRepository.findAll(pageable);
        List<Aluno> listOfAluno = alunos.getContent();
        List<AlunoDTO> content = listOfAluno.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setContent(content);
        alunoResponse.setPageNo(alunos.getNumber());
        alunoResponse.setPageSize(alunos.getSize());
        alunoResponse.setTotalElements(alunos.getTotalElements());
        alunoResponse.setTotalPages(alunos.getTotalPages());
        alunoResponse.setLast(alunos.isLast());

        return alunoResponse;
    }

    @Override
    public AlunoDTO getAlunoById(int id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Aluno não encontrado com o ID informado."));
        return mapToDto(aluno);
    }

    @Override
    public AlunoDTO updateAlunoById(AlunoDTO alunoDTO, int id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Aluno não encontrado com o ID informado."));

        aluno.setNome(alunoDTO.getNome());
        aluno.setCpf(alunoDTO.getCpf());

        Aluno updatedAluno = alunoRepository.save(aluno);
        return mapToDto(updatedAluno);
    }

    @Override
    public void deleteAlunoById(int id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Aluno não encontrado com o ID informado."));
        alunoRepository.delete(aluno);
    }

    private AlunoDTO mapToDto(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setNome(aluno.getNome());
        alunoDTO.setCpf(aluno.getCpf());
        return alunoDTO;
    }

}
