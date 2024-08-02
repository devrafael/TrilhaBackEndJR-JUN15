package com.cordigocerto.trilhabackend.controllers;

import com.cordigocerto.trilhabackend.controllers.dtos.requests.UsuarioRequest;
import com.cordigocerto.trilhabackend.controllers.dtos.responses.UsuarioResponse;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> buscarTodosUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.buscarUsuarios();
        List<UsuarioResponse> listaUsuarioResponses = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            listaUsuarioResponses.add(new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getRole()));
        }

        return ResponseEntity.ok().body(listaUsuarioResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok().body(new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getRole()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        usuarioService.deletarUsuario(usuario.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> criarUsuarioAdmin(@RequestBody UsuarioRequest usuarioRequest){
        Usuario novoUsuario = usuarioService.criarUsuarioAdmin(usuarioRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Void> criarUsuarioComum(@RequestBody UsuarioRequest usuarioRequest){
        Usuario novoUsuario = usuarioService.criarUsuarioComum(usuarioRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioRequest usuarioRequest, @PathVariable Long id){
        usuarioService.atualizarUsuario(usuarioRequest, id);
        return ResponseEntity.noContent().build();
    }

}
