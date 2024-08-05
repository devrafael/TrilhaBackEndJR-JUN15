package com.cordigocerto.trilhabackend.entities;


import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Column(unique = true, nullable = false, length = 60)
    private String login;

    @Column(length = 60, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String senha;

    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ManyToMany(cascade =  {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Role> role;

    @OneToMany(mappedBy = "usuario")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Tarefa> tarefa = new ArrayList<Tarefa>();

    public Usuario(Long id){
        this.id = id;
    }

    public boolean LoginCorreto(AutenticacaoRequest authRequest, BCryptPasswordEncoder passwordEncoder) {
       return passwordEncoder.matches(authRequest.senha(), this.senha);
    }
}
