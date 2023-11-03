package com.facens.devopsproject.controller;

import com.facens.devopsproject.controllers.CursoController;
import com.facens.devopsproject.dto.CursoDTO;
import com.facens.devopsproject.dto.CursoResponse;
import com.facens.devopsproject.models.Curso;
import com.facens.devopsproject.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CursoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;
    private Curso curso;
    private CursoDTO cursoDTO;

    @BeforeEach
    public void init() {
        curso = Curso.builder().nome("Java Basic to Advanced").duracaoEmHoras(60).build();
        cursoDTO = CursoDTO.builder().nome("Java Basic to Advanced").duracaoEmHoras(60).build();
    }

    @Test
    public void CursoController_CreateCurso_ReturnCreated() throws Exception {
        given(cursoService.createCurso(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/curso")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(cursoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duracaoEmHoras", CoreMatchers.is(cursoDTO.getDuracaoEmHoras())));
    }

    @Test
    public void CursoController_GetAllCurso_ReturnResponseDto() throws Exception {
        CursoResponse responseDto = CursoResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(cursoDTO)).build();
        when(cursoService.getAllCursos(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/curso")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void CursoController_CursoDetail_ReturnCursoDto() throws Exception {
        int cursoId = 1;
        when(cursoService.getCursoById(cursoId)).thenReturn(cursoDTO);

        ResultActions response = mockMvc.perform(get("/curso/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(cursoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duracaoEmHoras", CoreMatchers.is(cursoDTO.getDuracaoEmHoras())));
    }

    @Test
    public void CursoController_UpdateCurso_ReturnCursoDto() throws Exception {
        int cursoId = 1;
        when(cursoService.updateCursoById(cursoDTO, cursoId)).thenReturn(cursoDTO);

        ResultActions response = mockMvc.perform(put("/curso/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(cursoDTO.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duracaoEmHoras", CoreMatchers.is(cursoDTO.getDuracaoEmHoras())));
    }

    @Test
    public void CursoController_DeleteCurso_ReturnString() throws Exception {
        int cursoId = 1;
        doNothing().when(cursoService).deleteCursoById(1);

        ResultActions response = mockMvc.perform(delete("/curso/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}