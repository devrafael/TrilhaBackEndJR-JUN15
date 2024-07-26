package com.cordigocerto.trilhabackend.services;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.UsuarioRequest;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import com.cordigocerto.trilhabackend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado na base de dados! Id: " + id));
    }

    public void deletarUsuario(Long id) {
        Usuario u = buscarUsuario(id);
        usuarioRepository.delete(u);
    }

    public Usuario criarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuarioJaExiste = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuarioJaExiste != null) {
            throw new DataIntegrityViolationException("Já existe um usuário com essas credenciais!");
        }


        Usuario entity = new Usuario(usuarioRequest.nome(), usuarioRequest.senha(), usuarioRequest.login(), usuarioRequest.role());
        return usuarioRepository.save(entity);
    }

    public Usuario atualizarUsuario(UsuarioRequest usuarioRequest, Long id) {
        Usuario usuarioAtualizado = buscarUsuario(id);

        usuarioAtualizado.setNome(usuarioRequest.nome());
        usuarioAtualizado.setSenha(usuarioRequest.senha());
        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }



}
