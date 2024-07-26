package com.cordigocerto.trilhabackend.controllers.dtos.responses;

import com.cordigocerto.trilhabackend.entities.RoleEnum;

public record UsuarioResponse(Long id, String nome, String login, RoleEnum role) {
}
