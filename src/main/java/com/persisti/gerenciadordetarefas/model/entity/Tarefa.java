package com.persisti.gerenciadordetarefas.model.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "concluida")
    private boolean concluida;

//    @Override
//    public String toString() {
//        return "Tarefa{" +
//                "id= " + id +
//                ", nome= '" + nome + '\'' +
//                ", concluida= " + concluida +
//                '}';
//    }
}