package com.pfsprin.proyectofinalspring.Servicios;

import com.pfsprin.proyectofinalspring.Entidadess.Usuario;
import com.pfsprin.proyectofinalspring.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(Long.valueOf(id)).orElse(null);
    }

    // Guardar un nuevo usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario por su ID
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(Long.valueOf(id));
    }

    // Actualizar el rol de un usuario
    public Usuario actualizarRolUsuario(Integer id, boolean esAdmin) {
        Usuario usuario = obtenerUsuarioPorId(id);
        if (usuario != null) {
            usuario.setEsAdmin(esAdmin);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        // Retorna todos los usuarios almacenados en la base de datos
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Usuario obtenerPorId(Integer id) {
        // Verifica si el usuario con el ID proporcionado existe
        Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(id));
        if (usuario.isPresent()) {
            return usuario.get();  // Retorna el usuario si se encuentra
        } else {
            return null;  // Retorna null si no se encuentra el usuario
        }
    }

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        // Guarda el nuevo usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    public Usuario iniciarSesion(String correoElectronico, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Verificamos la contraseña usando BCrypt
        if (passwordEncoder.matches(contraseña, usuario.getContraseñaUsuario())) {
            return usuario;
        } else {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
    }
}
