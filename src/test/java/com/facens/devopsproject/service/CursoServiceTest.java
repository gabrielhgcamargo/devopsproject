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

import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;
import com.facens.devopsproject.models.Curso;
import com.facens.devopsproject.repository.CursoRepository;
import com.facens.devopsproject.service.impl.CursoServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    @Test
    public void CursoService_CreateCurso_ReturnsCursoDto() {
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        CursoDTO cursoDTO = CursoDTO.builder().nome("Java Basic to Advanced").duracaoEmHoras(60).build();

        when(cursoRepository.save(Mockito.any(Curso.class))).thenReturn(curso);

        CursoDTO savedCurso = cursoService.createCurso(cursoDTO);

        Assertions.assertThat(savedCurso).isNotNull();
    }

    @Test
    public void CursoService_GetAllCurso_ReturnsResponseDto() {
        Page<Curso> cursos = Mockito.mock(Page.class);

        when(cursoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(cursos);

        CursoResponse saveCurso = cursoService.getAllCursos(1,10);

        Assertions.assertThat(saveCurso).isNotNull();
    }
    
    @Test
    public void CursoService_FindById_ReturnCursoDto() {
        int cursoId = 1;
        Curso curso = Curso.builder().id(1).nome("Java Basic to Advanced").duracaoEmHoras(60).build();
        when(cursoRepository.findById(cursoId)).thenReturn(Optional.ofNullable(curso));

        CursoDTO cursoReturn = cursoService.getCursoById(cursoId);

        Assertions.assertThat(cursoReturn).isNotNull();
    }

    @Test
    public void CursoService_UpdateCurso_ReturnCursoDto() {
        int cursoId = 1;
        Curso curso = Curso.builder().id(1).nome("Java Basic to Advanced").duracaoEmHoras(60).build();
        CursoDTO cursoDto = CursoDTO.builder().id(1).nome("Java Basic to Advanced").duracaoEmHoras(60).build();

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.ofNullable(curso));
        when(cursoRepository.save(curso)).thenReturn(curso);

        CursoDTO updateReturn = cursoService.updateCursoById(cursoDto, cursoId);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void CursoService_DeleteCursoById_ReturnVoid() {
        int cursoId = 1;
        Curso curso = Curso.builder().id(1).nome("Java Basic to Advanced").duracaoEmHoras(60).build();

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.ofNullable(curso));
        doNothing().when(cursoRepository).delete(curso);

        assertAll(() -> cursoService.deleteCursoById(cursoId));
    }
}