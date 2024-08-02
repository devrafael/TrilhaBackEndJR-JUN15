package com.cordigocerto.trilhabackend.repositories;

import com.cordigocerto.trilhabackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNome(String nome);
}
