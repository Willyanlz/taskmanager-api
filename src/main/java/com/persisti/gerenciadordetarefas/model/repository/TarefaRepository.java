package com.persisti.gerenciadordetarefas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persisti.gerenciadordetarefas.model.entity.Tarefa;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    List<Tarefa> findAll();
}
