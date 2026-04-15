package com.example.hackathon.controller;

import com.example.hackathon.dto.DonacionDTO;
import com.example.hackathon.exception.ResourceNotFoundException;
import com.example.hackathon.model.Donacion;
import com.example.hackathon.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/donaciones", "/api/donaciones"})
@Tag(name = "Donaciones", description = "API para gestionar donaciones")
public class DonacionController {

    private final DonacionService service;

    public DonacionController(DonacionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las donaciones")
    public ResponseEntity<List<Donacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/mis")
    @Operation(summary = "Obtener las donaciones del donador autenticado")
    public ResponseEntity<List<Donacion>> listarMisDonaciones(Authentication authentication) {
        return ResponseEntity.ok(service.listarPorDonador(authentication.getName()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener donacion por ID")
    public ResponseEntity<Donacion> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Donacion con ID " + id + " no encontrada"));
    }

    @PostMapping
    @Operation(summary = "Crear nueva donacion")
    public ResponseEntity<Donacion> guardar(@Valid @RequestBody DonacionDTO donacion, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(donacion, authentication.getName()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar donacion existente")
    public ResponseEntity<Donacion> actualizar(@PathVariable Long id, @Valid @RequestBody Donacion donacion) {
        return ResponseEntity.ok(service.actualizar(id, donacion));
    }

    @PatchMapping("/{id}/reservar")
    @Operation(summary = "Reservar una donacion")
    public ResponseEntity<Donacion> reservar(@PathVariable Long id) {
        return ResponseEntity.ok(service.reservar(id));
    }

    @PatchMapping("/{id}/cancelar-reserva")
    @Operation(summary = "Cancelar la reserva de una donacion")
    public ResponseEntity<Donacion> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarReserva(id));
    }

    @PatchMapping("/{id}/recogido")
    @Operation(summary = "Marcar donacion como recogida")
    public ResponseEntity<Void> marcarRecogido(@PathVariable Long id) {
        service.marcarRecogido(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar donacion")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication authentication) {
        service.eliminarPropia(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
