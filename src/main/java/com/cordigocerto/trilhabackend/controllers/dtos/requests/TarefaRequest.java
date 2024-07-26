package com.cordigocerto.trilhabackend.controllers.dtos.requests;

import com.cordigocerto.trilhabackend.entities.Tarefa;

public record TarefaRequest(String tarefaDescricao, Long usuario_id) {

    public Tarefa toTarefa(){
        return new Tarefa(tarefaDescricao, usuario_id);
    }
}
