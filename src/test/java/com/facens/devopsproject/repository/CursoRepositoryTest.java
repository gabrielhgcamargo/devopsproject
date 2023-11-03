package com.facens.devopsproject.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.facens.devopsproject.models.Curso;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CursoRepositoryTest {
    
    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void CursoRepository_SaveAll_ReturnSavedCurso() {

        //Arrange
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        //Act
        Curso savedCurso = cursoRepository.save(curso);

        //Assert
        Assertions.assertThat(savedCurso).isNotNull();
        Assertions.assertThat(savedCurso.getId()).isGreaterThan(0);
    }

    @Test
    public void CursoRepository_GetAll_ReturnMoreThenOneCurso() {
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();
        Curso curso2 = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        cursoRepository.save(curso);
        cursoRepository.save(curso2);

        List<Curso> cursoList = cursoRepository.findAll();

        Assertions.assertThat(cursoList).isNotNull();
        Assertions.assertThat(cursoList.size()).isEqualTo(2);
    }

    @Test
    public void CursoRepository_FindById_ReturnCurso() {
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        cursoRepository.save(curso);

        Curso cursoList = cursoRepository.findById(curso.getId()).get();

        Assertions.assertThat(cursoList).isNotNull();
    }

    @Test
    public void CursoRepository_UpdateCurso_ReturnCursoNotNull() {
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        cursoRepository.save(curso);

        Curso cursoSave = cursoRepository.findById(curso.getId()).get();
        cursoSave.setNome("Java Basic to Advanced");
        cursoSave.setDuracaoEmHoras(60);

        Curso updatedCurso = cursoRepository.save(cursoSave);

        Assertions.assertThat(updatedCurso.getNome()).isNotNull();
        Assertions.assertThat(updatedCurso.getDuracaoEmHoras()).isNotNull();
    }

    @Test
    public void CursoRepository_CursoDelete_CursoIsEmpty() {
        Curso curso = Curso.builder()
                .nome("Java Basic to Advanced")
                .duracaoEmHoras(60).build();

        cursoRepository.save(curso);

        cursoRepository.deleteById(curso.getId());
        Optional<Curso> cursoReturn = cursoRepository.findById(curso.getId());

        Assertions.assertThat(cursoReturn).isEmpty();
    }

}
