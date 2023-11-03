package com.facens.devopsproject.service;

import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;

public interface CursoService {

    CursoDTO createCurso(CursoDTO cursoDTO);
    CursoResponse getAllCursos(int pageNo, int pageSize);
    CursoDTO getCursoById(int id);
    CursoDTO updateCursoById(CursoDTO cursoDTO, int id);
    void deleteCursoById(int id);
    
}
