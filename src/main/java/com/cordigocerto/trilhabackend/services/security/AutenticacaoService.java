package com.cordigocerto.trilhabackend.services.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.cordigocerto.trilhabackend.entities.Role;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioService usuarioService;

    public JwtClaimsSet getToken(AutenticacaoRequest authRequest) {
        Optional<Usuario> usuario = usuarioService.buscarPorLogin(authRequest);

        return createToken(usuario.orElse(null));
    }

    public JwtClaimsSet createToken(Usuario usuario) {

        try {
            var scopes = usuario.getRole()
                    .stream()
                    .map(Role::getNome)
                    .collect(Collectors.joining(" "));

            return JwtClaimsSet.builder()
                    .issuer("project-api")
                    .subject(usuario.getId().toString())
                    .issuedAt(Instant.now())
                    .expiresAt(getDateExpirationToken())
                    .claim("scope", scopes)
                    .build();

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao tentar gerar o token! " + e.getMessage());
        }
    }

    private Instant dateExpiration() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public Instant getDateExpirationToken(){
        return dateExpiration();
    }





}
