package com.cordigocerto.trilhabackend.services;

import com.cordigocerto.trilhabackend.entities.Tarefa;
import com.cordigocerto.trilhabackend.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> buscarTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return tarefas;
    }

    public List<Tarefa> buscarTodasTarefasPorUsuario_Id(Long usuario_id){
        List<Tarefa> tarefas = tarefaRepository.findByUsuario_Id(usuario_id);
        return tarefas;
    }

    public Tarefa buscarTarefa(Long id) {
        Optional<Tarefa> usuario = tarefaRepository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario nao encontrad! Id: " + id + ", Tipo: " + Tarefa.class.getName()));
    }

    public void deletarUsuario(Tarefa tarefa) {
        Tarefa usuario = buscarTarefa(tarefa.getTarefaId());
        tarefaRepository.delete(usuario);
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Tarefa tarefa, Long id) {
        Tarefa tarefaAtualizada = buscarTarefa(id);
        tarefaAtualizada.setTarefaDescricao(tarefa.getTarefaDescricao());
        tarefaRepository.save(tarefaAtualizada);
        return tarefaAtualizada;
    }
}
