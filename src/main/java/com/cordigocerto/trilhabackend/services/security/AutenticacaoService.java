package com.cordigocerto.trilhabackend.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AutenticacaoService implements TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public String getToken(AutenticacaoRequest autenticacaoRequest) {

        Usuario usuario = usuarioRepository.findByLogin(autenticacaoRequest.login());

        return createToken(usuario);
    }

    public String createToken(Usuario usuario) {

        try {

            Instant now = Instant.now();
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.create()
                    .withIssuer("project-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dateExpiration())
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao tentar gerar o token! " + e.getMessage());
        }
    }

    public String tokenValidation(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.require(algorithm)
                    .withIssuer("project-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            System.out.println("Erro na verificação do token: " + e.getMessage());
        }
        return "";
    }

    private Instant dateExpiration() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public Instant getDateExpirationToken(){
        return dateExpiration();
    }
}
