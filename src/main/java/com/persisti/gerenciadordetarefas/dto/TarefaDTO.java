package com.persisti.gerenciadordetarefas.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDTO {
    private int id;
    private String nome;
    private boolean concluida;
}
