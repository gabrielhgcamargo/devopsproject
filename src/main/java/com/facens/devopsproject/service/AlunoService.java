package com.facens.devopsproject.service;

import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;

public interface AlunoService {

    AlunoDTO createAluno(AlunoDTO alunoDTO);
    AlunoResponse getAllAlunos(int pageNo, int pageSize);
    AlunoDTO getAlunoById(int id);
    AlunoDTO updateAlunoById(AlunoDTO alunoDTO, int id);
    void deleteAlunoById(int id);

}