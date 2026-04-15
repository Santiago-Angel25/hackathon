package com.example.hackathon.controller;

import java.util.List;
import java.util.Optional;

import com.example.hackathon.service.DonacionService;
import com.example.hackathon.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hackathon.model.Donacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/donaciones")
@Tag(name = "Donaciones", description = "API para gestionar donaciones")
public class DonacionController {

    @Autowired
    private DonacionService service;

    @GetMapping
    @Operation(summary = "Obtener todas las donaciones")
    public ResponseEntity<List<Donacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener donación por ID")
    public ResponseEntity<Donacion> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Donación con ID " + id + " no encontrada"));
    }

    @PostMapping
    @Operation(summary = "Crear nueva donación")
    public ResponseEntity<Donacion> guardar(@Valid @RequestBody Donacion donacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(donacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar donación existente")
    public ResponseEntity<Donacion> actualizar(@PathVariable Long id, @Valid @RequestBody Donacion donacion) {
        return ResponseEntity.ok(service.actualizar(id, donacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar donación")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}