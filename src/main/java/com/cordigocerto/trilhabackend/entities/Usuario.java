package com.cordigocerto.trilhabackend.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {

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

    @Column(nullable = false)
    private RoleEnum role;

    @OneToMany(mappedBy = "usuario")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Tarefa> tarefa = new ArrayList<Tarefa>();

    public Usuario (String nome, String senha, String login, RoleEnum role) {
        this.nome = nome;
        this.senha = senha;
        this.login = login;
        this.role = role;
    }

    public Usuario (Long id, String nome, String login, RoleEnum role) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.role = role;
    }

    public Usuario (Long id){
        this.id = id;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == RoleEnum.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
