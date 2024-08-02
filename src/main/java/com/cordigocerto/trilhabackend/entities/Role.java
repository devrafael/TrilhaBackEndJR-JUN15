package com.cordigocerto.trilhabackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(name = "nome")
    private String nome;

    @Getter
    public enum Values{
        ADMIN (1L),
        USER(2L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }
    }
}
