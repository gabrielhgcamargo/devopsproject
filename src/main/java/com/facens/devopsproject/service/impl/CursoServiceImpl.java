package com.facens.devopsproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;
import com.facens.devopsproject.exception.CursoNotFoundException;
import com.facens.devopsproject.models.Curso;
import com.facens.devopsproject.repository.CursoRepository;
import com.facens.devopsproject.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService{
    
    private CursoRepository cursoRepository;

    @Autowired
    public void CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public CursoDTO createCurso(CursoDTO cursoDto) {
        Curso curso = new Curso();
        curso.setNome(cursoDto.getNome());
        curso.setDuracaoEmHoras(cursoDto.getDuracaoEmHoras());

        Curso newCurso = cursoRepository.save(curso);

        CursoDTO cursoResponse = new CursoDTO();
        cursoResponse.setId(newCurso.getId());
        cursoResponse.setNome(newCurso.getNome());
        cursoResponse.setDuracaoEmHoras(newCurso.getDuracaoEmHoras());
        return cursoResponse;
    }

    @Override
    public CursoResponse getAllCursos(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        List<Curso> listOfCurso = cursos.getContent();
        List<CursoDTO> content = listOfCurso.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        CursoResponse cursoResponse = new CursoResponse();
        cursoResponse.setContent(content);
        cursoResponse.setPageNo(cursos.getNumber());
        cursoResponse.setPageSize(cursos.getSize());
        cursoResponse.setTotalElements(cursos.getTotalElements());
        cursoResponse.setTotalPages(cursos.getTotalPages());
        cursoResponse.setLast(cursos.isLast());

        return cursoResponse;
    }

    @Override
    public CursoDTO getCursoById(int id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID informado."));
        return mapToDto(curso);
    }

    @Override
    public CursoDTO updateCursoById(CursoDTO cursoDto, int id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID informado."));

        curso.setNome(cursoDto.getNome());
        curso.setDuracaoEmHoras(cursoDto.getDuracaoEmHoras());

        Curso updatedCurso = cursoRepository.save(curso);
        return mapToDto(updatedCurso);
    }

    @Override
    public void deleteCursoById(int id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID informado."));
        cursoRepository.delete(curso);
    }

    private CursoDTO mapToDto(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setNome(curso.getNome());
        cursoDTO.setDuracaoEmHoras(curso.getDuracaoEmHoras());
        return cursoDTO;
    }

}
