package com.cordigocerto.trilhabackend.controllers.security;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.cordigocerto.trilhabackend.services.security.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> auth(@RequestBody AutenticacaoRequest autenticacaoRequest){

        var usuarioAutenticationToken = new UsernamePasswordAuthenticationToken(autenticacaoRequest.login(), autenticacaoRequest.senha());
        authenticationManager.authenticate(usuarioAutenticationToken);

        return ResponseEntity.ok().body(autenticacaoService.getToken(autenticacaoRequest));
    }


}
