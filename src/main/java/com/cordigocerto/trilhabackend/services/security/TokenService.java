package com.cordigocerto.trilhabackend.services.security;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface TokenService extends UserDetailsService {

    public String getToken(AutenticacaoRequest autenticacaoRequest);

}
