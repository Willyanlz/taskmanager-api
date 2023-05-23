package com.persisti.gerenciadordetarefas.service;

import com.persisti.gerenciadordetarefas.dto.TarefaDTO;
import com.persisti.gerenciadordetarefas.model.entity.Tarefa;
import com.persisti.gerenciadordetarefas.model.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    private TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<TarefaDTO> getAllTarefas() {
        return tarefaRepository.findAll().stream()
                .map(t -> new TarefaDTO(t.getId(), t.getNome(), t.isConcluida()))
                .collect(Collectors.toList());
    }

    public Optional<Tarefa> getTarefaById(Integer id) {
        return tarefaRepository.findById(id);
    }

    public void adicionarTarefa(TarefaDTO tarefaDto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(tarefaDto.getNome());
        tarefa.setConcluida(false);
        tarefaRepository.save(tarefa);
    }

    public Optional<TarefaDTO> atualizarTarefa(Integer id, TarefaDTO tarefaDto) {
        Optional<Tarefa> optionalTarefa = tarefaRepository.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setNome(tarefaDto.getNome());
            tarefaRepository.save(tarefa);
            return Optional.of(new TarefaDTO(tarefa.getId(), tarefa.getNome(), tarefa.isConcluida()));
        }
        return Optional.empty();
    }

    public void deleteTarefa(Integer id) {
        tarefaRepository.deleteById(id);
    }

    public void atualizarStatusTarefa(Integer id) {
        Optional<Tarefa> optionalTarefa = tarefaRepository.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setConcluida(!tarefa.isConcluida());
            tarefaRepository.save(tarefa);
        }
    }
}
