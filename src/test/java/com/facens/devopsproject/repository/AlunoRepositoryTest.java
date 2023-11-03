package com.facens.devopsproject.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.facens.devopsproject.models.Aluno;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AlunoRepositoryTest {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void AlunoRepository_SaveAll_ReturnSavedAluno() {

        //Arrange
        Aluno aluno = Aluno.builder()
                .nome("Java Basic to Advanced")
                .cpf("12312312312").build();

        //Act
        Aluno savedAluno = alunoRepository.save(aluno);

        //Assert
        Assertions.assertThat(savedAluno).isNotNull();
        Assertions.assertThat(savedAluno.getId()).isGreaterThan(0);
    }

    @Test
    public void AlunoRepository_GetAll_ReturnMoreThenOneAluno() {
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();
        Aluno aluno2 = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        alunoRepository.save(aluno);
        alunoRepository.save(aluno2);

        List<Aluno> alunoList = alunoRepository.findAll();

        Assertions.assertThat(alunoList).isNotNull();
        Assertions.assertThat(alunoList.size()).isEqualTo(2);
    }

    @Test
    public void AlunoRepository_FindById_ReturnAluno() {
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        alunoRepository.save(aluno);

        Aluno alunoList = alunoRepository.findById(aluno.getId()).get();

        Assertions.assertThat(alunoList).isNotNull();
    }

    @Test
    public void AlunoRepository_UpdateAluno_ReturnAlunoNotNull() {
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        alunoRepository.save(aluno);

        Aluno alunoSave = alunoRepository.findById(aluno.getId()).get();
        alunoSave.setNome("Josh Green");
        alunoSave.setCpf("12312312312");

        Aluno updatedAluno = alunoRepository.save(alunoSave);

        Assertions.assertThat(updatedAluno.getNome()).isNotNull();
        Assertions.assertThat(updatedAluno.getCpf()).isNotNull();
    }

    @Test
    public void AlunoRepository_AlunoDelete_AlunoIsEmpty() {
        Aluno aluno = Aluno.builder()
                .nome("Josh Green")
                .cpf("12312312312").build();

        alunoRepository.save(aluno);

        alunoRepository.deleteById(aluno.getId());
        Optional<Aluno> alunoReturn = alunoRepository.findById(aluno.getId());

        Assertions.assertThat(alunoReturn).isEmpty();
    }

}
