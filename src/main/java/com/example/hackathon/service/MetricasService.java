package com.example.hackathon.service;

import com.example.hackathon.model.MetricasGenerales;
import com.example.hackathon.repository.MetricasGeneralesRepository;
import com.example.hackathon.repository.UsuarioRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MetricasService {

    private final MetricasGeneralesRepository metricasRepository;
    private final UsuarioRepository usuarioRepository;

    public MetricasService(MetricasGeneralesRepository metricasRepository, UsuarioRepository usuarioRepository) {
        this.metricasRepository = metricasRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, Long> obtenerMetricas(Long donacionesActivas) {
        MetricasGenerales metricas = obtenerOcrear();
        Map<String, Long> response = new HashMap<>();
        response.put("donacionesActivas", donacionesActivas);
        response.put("usuariosRegistrados", usuarioRepository.count());
        response.put("entregasRealizadas", metricas.getEntregasRealizadas());
        response.put("vidasImpactadas", metricas.getVidasImpactadas());
        return response;
    }

    public void registrarRecogida() {
        MetricasGenerales metricas = obtenerOcrear();
        metricas.setEntregasRealizadas(metricas.getEntregasRealizadas() + 1);
        metricas.setVidasImpactadas(metricas.getVidasImpactadas() + 1);
        metricasRepository.save(metricas);
    }

    private MetricasGenerales obtenerOcrear() {
        return metricasRepository.findById(1L).orElseGet(() -> {
            MetricasGenerales metricas = new MetricasGenerales();
            metricas.setId(1L);
            metricas.setEntregasRealizadas(0L);
            metricas.setVidasImpactadas(0L);
            return metricasRepository.save(metricas);
        });
    }
}
