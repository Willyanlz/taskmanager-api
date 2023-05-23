package com.persisti.gerenciadordetarefas.service;

import com.persisti.gerenciadordetarefas.dto.TarefaDTO;
import com.persisti.gerenciadordetarefas.model.entity.Tarefa;
import com.persisti.gerenciadordetarefas.model.repository.TarefaRepository;
import com.persisti.gerenciadordetarefas.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;


    @Test
    @DisplayName("Deve obter todas tarefas")
    void getAllTarefasTest() {
        // cenario
        List<Tarefa> tarefas = Arrays.asList(new Tarefa(), new Tarefa());
        when(tarefaRepository.findAll()).thenReturn(tarefas);

        // execucao
        List<TarefaDTO> result = tarefaService.getAllTarefas();

        // verificacao
        assertEquals(2, result.size());
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve obter tarefa por id")
    void getTarefaByIdTest() {
        // cenario
        Integer tarefaId = 1;
        Tarefa tarefa = new Tarefa();
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        // execucao
        Optional<Tarefa> result = tarefaService.getTarefaById(tarefaId);

        // verificacao
        assertTrue(result.isPresent());
        assertEquals(tarefa, result.get());
        verify(tarefaRepository, times(1)).findById(tarefaId);
    }

    @Test
    @DisplayName("Deve criar tarefa")
    void adicionarTarefaTest() {
        // cenario
        TarefaDTO tarefaDto = new TarefaDTO();
        tarefaDto.setNome("Nova Tarefa");

        // execucao
        tarefaService.adicionarTarefa(tarefaDto);

        // verificacao
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    @DisplayName("Deve atualziar uma tarefa")
    void atualizarTarefaTest() {
        // cenario
        Integer tarefaId = 1;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setNome("Tarefa Antiga");
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        TarefaDTO tarefaDto = new TarefaDTO();
        tarefaDto.setNome("Tarefa Atualizada");

        // execucao
        Optional<TarefaDTO> result = tarefaService.atualizarTarefa(tarefaId, tarefaDto);

        // verificacao
        assertTrue(result.isPresent());
        assertEquals(tarefaId, result.get().getId());
        assertEquals(tarefaDto.getNome(), result.get().getNome());
        verify(tarefaRepository, times(1)).findById(tarefaId);
        verify(tarefaRepository, times(1)).save(tarefa);
    }


    @Test
    @DisplayName("Deve deletar uma tarefa")
    void deleteTarefaTest() {
        // cenario
        Integer tarefaId = 1;

        // execucao
        tarefaService.deleteTarefa(tarefaId);

        // verificacao
        verify(tarefaRepository, times(1)).deleteById(tarefaId);
    }

    @Test
    @DisplayName("Deve atualizar o status de uma tarefa")
    void atualizarStatusTarefaTest() {
        // cenario
        Integer tarefaId = 1;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setConcluida(false);
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        // execucao
        tarefaService.atualizarStatusTarefa(tarefaId);

        // verificacao
        assertTrue(tarefa.isConcluida());
        verify(tarefaRepository, times(1)).findById(tarefaId);
        verify(tarefaRepository, times(1)).save(tarefa);
    }
}
