package com.cordigocerto.trilhabackend.config;

import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import com.cordigocerto.trilhabackend.services.security.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isH2ConsoleRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = this.getTokenHeader(request);
        if(token != null) {
           String login = autenticacaoService.tokenValidation(token);
           Usuario usuario = usuarioRepository.findByLogin(login);
           var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isH2ConsoleRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/h2-console/login.do");
    }


    public String getTokenHeader(HttpServletRequest resquest){
        var authHeader = resquest.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        String token = authHeader.replace("Bearer ", "");

        return token;
    }
}
