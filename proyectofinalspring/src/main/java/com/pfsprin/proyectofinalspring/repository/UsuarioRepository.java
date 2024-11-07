package com.pfsprin.proyectofinalspring.repository;

import com.pfsprin.proyectofinalspring.Entidadess.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
