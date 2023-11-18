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

        // Criando um objeto Aluno simulado
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        // Criando um objeto AlunoDTO simulado
        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Josh Green").cpf("12312312312").build();

        // Configurando o comportamento simulado do alunoRepository
        when(alunoRepository.save(Mockito.any(Aluno.class))).thenReturn(aluno);

        // Chamando o método do serviço para criar um aluno
        AlunoDTO savedAluno = alunoService.createAluno(alunoDTO);

        // Verificando se o AlunoDTO retornado não é nulo
        Assertions.assertThat(savedAluno).isNotNull();
    }

    @Test
    public void AlunoService_GetAllAluno_ReturnsResponseDto() {

        // Criando um objeto simulado para a classe Page<Aluno>
        Page<Aluno> alunos = Mockito.mock(Page.class);

        // Configurando o comportamento simulado do alunoRepository
        when(alunoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(alunos);

        // Chamando o método do serviço para obter todos os alunos
        AlunoResponse saveAluno = alunoService.getAllAlunos(1,10);

        // Verificando se a resposta do AlunoResponse não é nula
        Assertions.assertThat(saveAluno).isNotNull();
    }
    
    @Test
    public void AlunoService_FindById_ReturnAlunoDto() {

        // ID simulado de um aluno
        int alunoId = 1;

        // Criando um objeto Aluno simulado
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();
        
        // Configurando o comportamento simulado do alunoRepository
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));

        // Chamando o método do serviço para obter um aluno por ID
        AlunoDTO alunoReturn = alunoService.getAlunoById(alunoId);

        // Verificando se o AlunoDTO retornado não é nulo
        Assertions.assertThat(alunoReturn).isNotNull();
    }

    @Test
    public void AlunoService_UpdateAluno_ReturnAlunoDto() {
        
        // ID simulado de um aluno
        int alunoId = 1;

        // Criando um objeto Aluno simulado
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();
        
        // Criando um objeto AlunoDTO simulado para a atualização
        AlunoDTO alunoDto = AlunoDTO.builder().id(1).nome("Josh Green").cpf("12312312312").build();

        // Configurando o comportamento simulado do alunoRepository
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        // Chamando o método do serviço para atualizar um aluno
        AlunoDTO updateReturn = alunoService.updateAlunoById(alunoDto, alunoId);

        // Verificando se o AlunoDTO retornado não é nulo
        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void AlunoService_DeleteAlunoById_ReturnVoid() {

        // ID simulado de um aluno
        int alunoId = 1;

        // Criando um objeto Aluno simulado
        Aluno aluno = Aluno.builder().id(1).nome("Josh Green").cpf("12312312312").build();

        // Configurando o comportamento simulado do alunoRepository
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.ofNullable(aluno));
        doNothing().when(alunoRepository).delete(aluno);

        // Verificando se a deleção não lança exceções
        assertAll(() -> alunoService.deleteAlunoById(alunoId));
    }
}