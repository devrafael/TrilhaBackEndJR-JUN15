package com.cordigocerto.trilhabackend.controllers;

import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.buscarUsuarios();
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        usuarioService.deletarUsuario(usuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<Void> criarUsuario(@RequestBody Usuario usuario){
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id){
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }

}
