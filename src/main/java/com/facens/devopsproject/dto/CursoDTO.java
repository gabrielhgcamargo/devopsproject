package com.facens.devopsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {
    private int id;
    private String nome;
    private Integer duracaoEmHoras;
}