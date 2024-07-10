package com.cordigocerto.trilhabackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tarefaId;

    private String tarefaDescricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;



}
