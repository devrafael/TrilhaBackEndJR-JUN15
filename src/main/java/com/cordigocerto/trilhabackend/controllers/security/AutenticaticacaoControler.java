package com.cordigocerto.trilhabackend.controllers.security;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.cordigocerto.trilhabackend.controllers.dtos.responses.AutenticacaoResponse;
import com.cordigocerto.trilhabackend.services.security.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticaticacaoControler {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutenticacaoResponse> auth(@RequestBody AutenticacaoRequest authRequest){

        var x =  autenticacaoService.getToken(authRequest);

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(x)).getTokenValue();

        return ResponseEntity.ok().body(new AutenticacaoResponse(jwtValue, autenticacaoService.getDateExpirationToken()));
    }


}
