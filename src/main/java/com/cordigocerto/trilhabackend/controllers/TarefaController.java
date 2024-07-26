package com.cordigocerto.trilhabackend.controllers;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.TarefaRequest;
import com.cordigocerto.trilhabackend.entities.Tarefa;
import com.cordigocerto.trilhabackend.services.TarefaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/admin")
    public ResponseEntity<List<Tarefa>> buscarTodasTarefas(){
        List<Tarefa> listaTarefas = tarefaService.buscarTarefas();
        return ResponseEntity.ok(listaTarefas);
    }


    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<Tarefa>> buscarTodasTarefasPorUsuario_Id(@PathVariable Long id){
        List<Tarefa> tarefas = tarefaService.buscarTodasTarefasPorUsuario_Id(id);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaEspecifica(@PathVariable Long id){
        Tarefa t = tarefaService.buscarTarefa(id);
        return ResponseEntity.ok(t);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
        Tarefa tarefa = tarefaService.buscarTarefa(id);
        tarefaService.deletarUsuario(tarefa);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Void> criarTarefa(@RequestBody TarefaRequest tarefaRequest){
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefaRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novaTarefa.getTarefaId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> atualizarTarefa(@RequestBody TarefaRequest tarefaRequest, @PathVariable Long id){
        Tarefa tarefaAtual = tarefaService.atualizarTarefa(tarefaRequest, id);
        return ResponseEntity.noContent().build();
    }




}
