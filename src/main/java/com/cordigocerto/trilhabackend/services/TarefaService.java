package com.cordigocerto.trilhabackend.services;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.TarefaRequest;
import com.cordigocerto.trilhabackend.entities.Tarefa;
import com.cordigocerto.trilhabackend.repositories.TarefaRepository;
import com.cordigocerto.trilhabackend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Tarefa> buscarTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return tarefas;
    }

    public List<Tarefa> buscarTodasTarefasPorUsuario_Id(Long usuario_id){
        if(usuarioService.buscarUsuario(usuario_id) == null){
            throw new ResourceNotFoundException("Usuario nao encontrado na base de dados! Id: " + usuario_id);
        }
        List<Tarefa> tarefas = tarefaRepository.findByUsuario_Id(usuario_id);
        return tarefas;
    }

    public Tarefa buscarTarefa(Long id) {
        Optional<Tarefa> usuario = tarefaRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada na base de dados! Id: " + id));
    }

    public void deletarUsuario(Tarefa tarefa) {
        Tarefa usuario = buscarTarefa(tarefa.getTarefaId());
        tarefaRepository.delete(usuario);
    }

    public Tarefa criarTarefa(TarefaRequest tarefaRequest) {
        return tarefaRepository.save(new Tarefa(tarefaRequest.tarefaDescricao(), tarefaRequest.usuario_id()));
    }

    public Tarefa atualizarTarefa(TarefaRequest tarefaRequest, Long id) {
        Tarefa tarefaAtualizada = buscarTarefa(id);
        tarefaAtualizada.setTarefaDescricao(tarefaRequest.tarefaDescricao());
        tarefaRepository.save(tarefaAtualizada);
        return tarefaAtualizada;
    }
}
