package com.example.hackathon.controller;

import com.example.hackathon.dto.RegistroUsuarioDTO;
import com.example.hackathon.model.Usuario;
import com.example.hackathon.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody RegistroUsuarioDTO dto) {
        Usuario usuario = usuarioService.registrar(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("id", usuario.getId());
        response.put("nombre", usuario.getNombre());
        response.put("email", usuario.getEmail());
        response.put("rol", usuario.getRol().name());
        response.put("mensaje", "Usuario registrado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
