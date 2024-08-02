package com.cordigocerto.trilhabackend.controllers;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.TarefaRequest;
import com.cordigocerto.trilhabackend.entities.Tarefa;
import com.cordigocerto.trilhabackend.services.TarefaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Tarefa>> buscarTodasTarefas(){
        List<Tarefa> listaTarefas = tarefaService.buscarTarefas();
        return ResponseEntity.ok(listaTarefas);
    }


    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<Tarefa>> buscarTodasTarefasPorUsuario_Id(@PathVariable Long id, JwtAuthenticationToken token){
        List<Tarefa> tarefas;
        if(id.equals(Long.valueOf(token.getName()))){
           tarefas = tarefaService.buscarTodasTarefasPorUsuario_Id(id);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(tarefas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaEspecifica(@PathVariable Long id, JwtAuthenticationToken token){
        Tarefa t = tarefaService.buscarTarefa(id);
        if (t.getUsuario().getId().equals(Long.valueOf(token.getName()))) {
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id, JwtAuthenticationToken token){
        Tarefa tarefa = tarefaService.buscarTarefa(id);
        if(tarefa.getUsuario().getId().equals(Long.valueOf(token.getName()))){
            tarefaService.deletarTarefa(tarefa, token);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Void> criarTarefa(@RequestBody TarefaRequest tarefaRequest, JwtAuthenticationToken token){
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefaRequest, token);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novaTarefa.getTarefaId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> atualizarTarefa(@RequestBody TarefaRequest tarefaRequest, @PathVariable Long id, JwtAuthenticationToken token){
        Tarefa tarefaAtual = tarefaService.buscarTarefa(id);
        if(tarefaAtual.getUsuario().getId().equals(Long.valueOf(token.getName()))){
            tarefaService.atualizarTarefa(tarefaRequest, id);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }




}
