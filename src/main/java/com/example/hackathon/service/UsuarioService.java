package com.example.hackathon.service;

import com.example.hackathon.dto.RegistroUsuarioDTO;
import com.example.hackathon.model.Usuario;
import com.example.hackathon.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(RegistroUsuarioDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }

        String email = dto.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre().trim());
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setTelefono(dto.getTelefono().trim());
        usuario.setDireccion(dto.getDireccion() == null ? null : dto.getDireccion().trim());
        usuario.setDocumento(dto.getDocumento() == null ? null : dto.getDocumento().trim());
        usuario.setTipoPersona(dto.getTipoPersona() == null ? null : dto.getTipoPersona().trim());
        usuario.setRol(dto.getRol());

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}
