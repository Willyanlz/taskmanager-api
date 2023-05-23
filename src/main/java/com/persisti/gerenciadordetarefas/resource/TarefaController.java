package com.persisti.gerenciadordetarefas.resource;

import com.persisti.gerenciadordetarefas.dto.TarefaDTO;
import com.persisti.gerenciadordetarefas.model.entity.Tarefa;
import com.persisti.gerenciadordetarefas.service.TarefaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tarefas")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api("Gerenciador de tarefas API")
@Slf4j
public class TarefaController {

    private TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    @ApiOperation("Get all tarefas")
    public ResponseEntity<List<TarefaDTO>> getAll() {
        List<TarefaDTO> lista = tarefaService.getAllTarefas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Get tarefa by id")
    public ResponseEntity<Optional<Tarefa>> getById(@PathVariable Integer id) {
        Optional<Tarefa> tarefa;
        tarefa = tarefaService.getTarefaById(id);
        try {
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation("Create new tarefa")
    public void adicionarTarefa(@RequestBody TarefaDTO tarefaDto) {
        tarefaService.adicionarTarefa(tarefaDto);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update tarefa")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Integer id, @RequestBody TarefaDTO tarefaDto) {
        Optional<TarefaDTO> optionalTarefaDto = tarefaService.atualizarTarefa(id, tarefaDto);
        if (optionalTarefaDto.isPresent()) {
            return ResponseEntity.ok(optionalTarefaDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Delete tarefa")
    public void deleteTarefa(@PathVariable Integer id) {
        tarefaService.deleteTarefa(id);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation("Update only status of tarefa")
    public void atualizarStatus(@PathVariable Integer id) {
        tarefaService.atualizarStatusTarefa(id);
    }
}
