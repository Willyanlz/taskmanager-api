package com.persisti.gerenciadordetarefas.resource;

import com.persisti.gerenciadordetarefas.dto.TarefaDTO;
import com.persisti.gerenciadordetarefas.model.entity.Tarefa;
import com.persisti.gerenciadordetarefas.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("teste")
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    @Test
    @DisplayName("Deve todas tarefas")
    void getAllTest() {
        // cenario
        List<TarefaDTO> tarefas = Arrays.asList(new TarefaDTO(), new TarefaDTO());
        when(tarefaService.getAllTarefas()).thenReturn(tarefas);

        // execucao
        ResponseEntity<List<TarefaDTO>> response = tarefaController.getAll();

        // verificacao
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefas, response.getBody());
        verify(tarefaService, times(1)).getAllTarefas();
    }

    @Test
    @DisplayName("Deve obter tarefa por id")
    void getByIdTest() {
        // cenario
        Integer tarefaId = 1;
        Optional<Tarefa> tarefa = Optional.of(new Tarefa());
        when(tarefaService.getTarefaById(tarefaId)).thenReturn(tarefa);

        // execucao
        ResponseEntity<Optional<Tarefa>> response = tarefaController.getById(tarefaId);

        // verificacao
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefa, response.getBody());
        verify(tarefaService, times(1)).getTarefaById(tarefaId);
    }

    @Test
    @DisplayName("Deve adicionar uma tarefa")
    void adicionarTarefaTest() {
        // cenario
        TarefaDTO tarefaDto = new TarefaDTO();

        // execucao
        tarefaController.adicionarTarefa(tarefaDto);

        // verificacao
        verify(tarefaService, times(1)).adicionarTarefa(tarefaDto);
    }

    @Test
    @DisplayName("Deve atualizar tarefa")
    void atualizarTarefaTest() {
        // cenario
        Integer tarefaId = 1;
        TarefaDTO tarefaDto = new TarefaDTO();
        Optional<TarefaDTO> updatedTarefaDto = Optional.of(new TarefaDTO());
        when(tarefaService.atualizarTarefa(tarefaId, tarefaDto)).thenReturn(updatedTarefaDto);

        // execucao
        ResponseEntity<TarefaDTO> response = tarefaController.atualizarTarefa(tarefaId, tarefaDto);

        // verificacao
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTarefaDto.get(), response.getBody());
        verify(tarefaService, times(1)).atualizarTarefa(tarefaId, tarefaDto);
    }

    @Test
    @DisplayName("Deve deletar uma tarefa")
    void deleteTarefaTest() {
        // cenario
        Integer tarefaId = 1;

        // execucao
        tarefaController.deleteTarefa(tarefaId);

        // verificacao
        verify(tarefaService, times(1)).deleteTarefa(tarefaId);
    }

    @Test
    void atualizarStatusTest() {
        // cenario
        Integer tarefaId = 1;

        // execucao
        tarefaController.atualizarStatus(tarefaId);

        // verificacao
        verify(tarefaService, times(1)).atualizarStatusTarefa(tarefaId);
    }
}
