package com.cordigocerto.trilhabackend.config;

import com.cordigocerto.trilhabackend.entities.Role;
import com.cordigocerto.trilhabackend.entities.Usuario;
import com.cordigocerto.trilhabackend.repositories.RoleRepository;
import com.cordigocerto.trilhabackend.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;


@Configuration
public class ConfigUsuarioAdmin implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name());

        var usuarioAdmin = usuarioRepository.findByLogin("admin@gmail.com");

        usuarioAdmin.ifPresentOrElse(
                (usuario) -> {
                    System.out.println("admin ja existe!");
                    },
                () -> {
                    var usuario = new Usuario();
                    usuario.setNome("rafael");
                    usuario.setLogin("admin@gmail.com");
                    usuario.setSenha(passwordEncoder.encode("123"));
                    usuario.setRole(Set.of(roleAdmin));
                    usuarioRepository.save(usuario);
                }
        );


    }
}
