package com.facens.devopsproject.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;
import com.facens.devopsproject.models.Aluno;
import com.facens.devopsproject.repository.AlunoRepository;
import com.facens.devopsproject.service.impl.AlunoServiceImpl;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoServiceImpl alunoService;

    @Test
    public void AlunoService_CreateAluno_ReturnsAlunoDto() {
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Josh Green").cpf("12312312312").build();

        when(alunoRepository.save(Mockito.any(Aluno.class))).thenReturn(aluno);

        AlunoDTO savedAluno = alunoService.createAluno(alunoDTO);

        Assertions.assertThat(savedAluno).isNotNull();
    }

    @Test
    public void AlunoService_GetAllAluno_ReturnsResponseDto() {
        Page<Aluno> alunos = Mockito.mock(Page.class);

        when(alunoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(alunos);

        AlunoResponse saveAluno = alunoService.getAllAlunos(1,10);

        Assertions.assertThat(saveAluno).isNotNull();
    }
    
    @Test
    public void AlunoService_FindById_ReturnAlunoDto() {
        int alunoId = 1;
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));

        AlunoDTO alunoReturn = alunoService.getAlunoById(alunoId);

        Assertions.assertThat(alunoReturn).isNotNull();
    }

    @Test
    public void AlunoService_UpdateAluno_ReturnAlunoDto() {
        int alunoId = 1;
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();
        AlunoDTO alunoDto = AlunoDTO.builder().id(1).nome("Josh Green").cpf("12312312312").build();

        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        AlunoDTO updateReturn = alunoService.updateAlunoById(alunoDto, alunoId);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void AlunoService_DeleteAlunoById_ReturnVoid() {
        int alunoId = 1;
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();

        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));
        doNothing().when(alunoRepository).delete(aluno);

        assertAll(() -> alunoService.deleteAlunoById(alunoId));
    }
}