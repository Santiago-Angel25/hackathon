package com.example.hackathon.controller;

import com.example.hackathon.service.MetricasService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class MetricasController {

    private final MetricasService metricasService;

    public MetricasController(MetricasService metricasService) {
        this.metricasService = metricasService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Long>> obtenerMetricas() {
        return ResponseEntity.ok(metricasService.obtenerMetricas());
    }
}
