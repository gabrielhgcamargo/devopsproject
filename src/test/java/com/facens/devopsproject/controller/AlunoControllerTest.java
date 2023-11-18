package com.facens.devopsproject.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.facens.devopsproject.controllers.AlunoController;
import com.facens.devopsproject.dto.AlunoDTO;
import com.facens.devopsproject.dto.AlunoResponse;
import com.facens.devopsproject.models.Aluno;
import com.facens.devopsproject.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = AlunoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AlunoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @Autowired
    private ObjectMapper objectMapper;
    private Aluno aluno;
    private AlunoDTO alunoDTO;

    @BeforeEach
    public void init() {
        aluno = Aluno.builder().nome("Josh Green").cpf("12312312312").build();
        alunoDTO = AlunoDTO.builder().nome("Josh Green").cpf("12312312312").build();
    }

    @Test
    public void AlunoController_CreateAluno_ReturnCreated() throws Exception {
        
        // Mockando o comportamento do AlunoService para simular a criação de um estudante
        given(alunoService.createAluno(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        // Realizando uma solicitação HTTP simulada para criar um estudante
        ResultActions response = mockMvc.perform(post("/aluno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)));
                
        // Verificando se a resposta HTTP está conforme o esperado
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(alunoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(alunoDTO.getCpf())));
    }

    @Test
    public void AlunoController_GetAllAluno_ReturnResponseDto() throws Exception {
        
        // Criando um mock AlunoResponse para o método getAllAlunos
        AlunoResponse responseDto = AlunoResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(alunoDTO)).build();
        when(alunoService.getAllAlunos(1,10)).thenReturn(responseDto);

         // Realizando uma solicitação HTTP simulada para obter todos os estudantes
        ResultActions response = mockMvc.perform(get("/aluno")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        // Verificando se a resposta HTTP contém o conteúdo esperado
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void AlunoController_AlunoDetail_ReturnAlunoDto() throws Exception {
        int alunoId = 1;

        // Mockando o comportamento do AlunoService para simular a obtenção de um estudante específico
        when(alunoService.getAlunoById(alunoId)).thenReturn(alunoDTO);
        
        // Realizando uma solicitação HTTP simulada para obter detalhes de um estudante específico
        ResultActions response = mockMvc.perform(get("/aluno/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)));

        // Verificando se a resposta HTTP contém os detalhes esperados
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(alunoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(alunoDTO.getCpf())));
    }

    @Test
    public void AlunoController_UpdateAluno_ReturnAlunoDto() throws Exception {
        int alunoId = 1;

        // Mockando o comportamento do AlunoService para simular a atualização de um estudante
        when(alunoService.updateAlunoById(alunoDTO, alunoId)).thenReturn(alunoDTO);

        // Realizando uma solicitação HTTP simulada para atualizar um estudante
        ResultActions response = mockMvc.perform(put("/aluno/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)));

        // Verificando se a resposta HTTP contém os detalhes atualizados
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(alunoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(alunoDTO.getCpf())));
    }

    @Test
    public void AlunoController_DeleteAluno() throws Exception {
        int alunoId = 1;

        // Mockando o comportamento do AlunoService para simular a exclusão de um estudante
        doNothing().when(alunoService).deleteAlunoById(alunoId);

        // Realizando uma solicitação HTTP simulada para excluir um estudante
        ResultActions response = mockMvc.perform(delete("/aluno/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Verificando se a resposta HTTP indica uma exclusão bem-sucedida
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
