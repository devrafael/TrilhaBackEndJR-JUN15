package com.cordigocerto.trilhabackend.controllers.dtos.responses;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


public class AutenticacaoResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private Instant dataExpiracacaoToken;

    public AutenticacaoResponseDTO(String token, Instant dataExpiracacaoToken) {
        this.token = token;
        this.dataExpiracacaoToken = dataExpiracacaoToken;
    }

    public String getToken() {
        return token;
    }

    public Instant getDataExpiracacaoToken() {
        return dataExpiracacaoToken;
    }
}
