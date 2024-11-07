package com.pfsprin.proyectofinalspring.Controladores;

import com.pfsprin.proyectofinalspring.Entidadess.LoginRequest;
import com.pfsprin.proyectofinalspring.Entidadess.Usuario;
import com.pfsprin.proyectofinalspring.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable Integer id) {
        return Optional.ofNullable(usuarioService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:5173") // Especifica el origen permitido
    @PostMapping("/iniciarSesion")
    public ResponseEntity<Usuario> iniciarSesion(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.iniciarSesion(loginRequest.getCorreoElectronico(), loginRequest.getContraseña());
        return ResponseEntity.ok(usuario);
    }
}
