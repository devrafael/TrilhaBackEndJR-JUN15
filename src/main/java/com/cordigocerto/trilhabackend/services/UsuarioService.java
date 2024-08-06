package com.cordigocerto.trilhabackend.services;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.AutenticacaoRequest;
import com.cordigocerto.trilhabackend.controllers.dtos.requests.UsuarioRequest;
import com.cordigocerto.trilhabackend.entities.Role;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.RoleRepository;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import com.cordigocerto.trilhabackend.services.exceptions.EmptyCredentialsException;
import com.cordigocerto.trilhabackend.services.exceptions.IncorrectCredentialsException;
import com.cordigocerto.trilhabackend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado na base de dados! Id: " + id));
    }

    public Optional<Usuario> buscarPorLogin(AutenticacaoRequest authRequest){
        var usuario = usuarioRepository.findByLogin(authRequest.login());

        if (usuario.isEmpty() || !(usuario.get().LoginCorreto(authRequest, passwordEncoder))) {
            throw new IncorrectCredentialsException("Credenciais incorretas! Favor verificar os dados de login.");
        }
        return usuario;
    }

    public void deletarUsuario(Long id) {
        Usuario u = buscarUsuario(id);


        usuarioRepository.delete(u);
    }

    public Usuario criarUsuarioComum(UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuarioJaExiste.isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com essas credenciais!");
        }

        var senhaHash = passwordEncoder.encode(usuarioRequest.senha());
        var roleUser = roleRepository.findByNome(Role.Values.USER.name());

        Usuario u = new Usuario();
        u.setLogin(usuarioRequest.login());
        u.setSenha(senhaHash);
        u.setNome(usuarioRequest.nome());
        u.setRole(Set.of(roleUser));
        return usuarioRepository.save(u);
    }

    public Usuario criarUsuarioAdmin(UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuarioJaExiste.isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com essas credenciais!");
        }

        var senhaHash = passwordEncoder.encode(usuarioRequest.senha());
        var roleUser = roleRepository.findByNome(Role.Values.ADMIN.name());

        Usuario u = new Usuario();
        u.setLogin(usuarioRequest.login());
        u.setSenha(senhaHash);
        u.setNome(usuarioRequest.nome());
        u.setRole(Set.of(roleUser));
        return usuarioRepository.save(u);
    }

    public Usuario atualizarUsuario(UsuarioRequest usuarioRequest, Long id) {
        Usuario usuarioAtualizado = buscarUsuario(id);
        var senhaHash = "";

        if(usuarioRequest.nome() == "" || usuarioRequest.senha() == ""){
            throw new EmptyCredentialsException("Não foi possivel atualizar, pois alguma credencial pode estar vazia!");
        } else if (usuarioRequest.nome() == null && usuarioRequest.senha() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequest.senha());
            usuarioAtualizado.setNome(usuarioAtualizado.getNome());
            usuarioAtualizado.setSenha(senhaHash);
        } else if (usuarioRequest.nome() != null && usuarioRequest.senha() == null) {
            usuarioAtualizado.setSenha(usuarioAtualizado.getSenha());
            usuarioAtualizado.setNome(usuarioRequest.nome());
        } else if (usuarioRequest.nome() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequest.senha());
            usuarioAtualizado.setNome(usuarioRequest.nome());
            usuarioAtualizado.setSenha(senhaHash);
        }

        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }



}
