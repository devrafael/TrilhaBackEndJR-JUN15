package com.cordigocerto.trilhabackend.controllers.dtos.responses;

import com.cordigocerto.trilhabackend.entities.Role;

import java.util.Set;

public record UsuarioResponse(Long id, String nome, String login, Set<Role> role) {
}
