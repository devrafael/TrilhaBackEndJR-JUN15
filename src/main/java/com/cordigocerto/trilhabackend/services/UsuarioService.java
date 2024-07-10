package com.cordigocerto.trilhabackend.services;

import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return usuario.orElseThrow(() -> new RuntimeException("Usuario nao encontrad! Id: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public void deletarUsuario(Usuario usuario) {
        Usuario u = buscarUsuario(usuario.getId());
        usuarioRepository.delete(u);
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario, Long id) {
        Usuario usuarioAtualizado = buscarUsuario(id);
        usuarioAtualizado.setNome(usuario.getNome());
        usuarioAtualizado.setSenha(usuario.getSenha());
        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }

}
