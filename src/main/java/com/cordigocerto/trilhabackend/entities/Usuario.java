package com.cordigocerto.trilhabackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 60)
    private String nome;

    @Column(length = 60, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Tarefa> tarefa = new ArrayList<Tarefa>();
}
