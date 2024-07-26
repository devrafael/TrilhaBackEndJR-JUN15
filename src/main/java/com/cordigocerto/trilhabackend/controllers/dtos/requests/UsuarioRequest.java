package com.cordigocerto.trilhabackend.controllers.dtos.requests;

import com.cordigocerto.trilhabackend.entities.RoleEnum;

public record UsuarioRequest(String nome, String senha, String login, RoleEnum role){
}
