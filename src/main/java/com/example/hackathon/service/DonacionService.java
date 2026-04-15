package com.example.hackathon.service;

import java.util.List;
import java.util.Optional;

import com.example.hackathon.model.Donacion;
import com.example.hackathon.repository.DonacionRepository;
import com.example.hackathon.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class DonacionService {

    private final DonacionRepository repo;


    public DonacionService(DonacionRepository repo) {
        this.repo = repo;
    }


    public List<Donacion> listar() {
        return repo.findAll();
    }


    public Optional<Donacion> obtenerPorId(Long id) {
        return repo.findById(id);
    }


    public Donacion guardar(Donacion donacion) {

        if (donacion.getDescripcion() == null || donacion.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (donacion.getTitulo() == null || donacion.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        return repo.save(donacion);
    }


    public Donacion actualizar(Long id, Donacion donacion) {

        Donacion existente = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Donación con ID " + id + " no encontrada")
                );

        if (donacion.getTitulo() != null) {
            existente.setTitulo(donacion.getTitulo());
        }

        if (donacion.getDescripcion() != null) {
            existente.setDescripcion(donacion.getDescripcion());
        }

        if (donacion.getCantidad() != null) {
            existente.setCantidad(donacion.getCantidad());
        }

        if (donacion.getEstado() != null) {
            existente.setEstado(donacion.getEstado());
        }

        return repo.save(existente);
    }


    public void eliminar(Long id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Donación con ID " + id + " no encontrada");
        }

        repo.deleteById(id);
    }
}