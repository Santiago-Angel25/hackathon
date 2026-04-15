package com.example.hackathon.service;

import com.example.hackathon.model.EstadoDonacion;
import com.example.hackathon.repository.DonacionRepository;
import com.example.hackathon.repository.UsuarioRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MetricasService {

    private final DonacionRepository donacionRepository;
    private final UsuarioRepository usuarioRepository;

    public MetricasService(DonacionRepository donacionRepository, UsuarioRepository usuarioRepository) {
        this.donacionRepository = donacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, Long> obtenerMetricas() {
        long entregasRealizadas = donacionRepository.countByEstado(EstadoDonacion.ENTREGADO);
        Map<String, Long> response = new HashMap<>();
        response.put("donacionesActivas", donacionRepository.countByEstadoNot(EstadoDonacion.ENTREGADO));
        response.put("usuariosRegistrados", usuarioRepository.count());
        response.put("entregasRealizadas", entregasRealizadas);
        response.put("vidasImpactadas", entregasRealizadas);
        return response;
    }
}
