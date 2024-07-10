package com.cordigocerto.trilhabackend.repositories;

import com.cordigocerto.trilhabackend.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByUsuario_Id(Long id);
}
