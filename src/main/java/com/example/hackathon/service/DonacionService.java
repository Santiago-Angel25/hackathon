package com.example.hackathon.service;

import com.example.hackathon.dto.DonacionDTO;
import com.example.hackathon.exception.ResourceNotFoundException;
import com.example.hackathon.model.Donacion;
import com.example.hackathon.model.EstadoDonacion;
import com.example.hackathon.model.TipoDonacion;
import com.example.hackathon.model.Ubicacion;
import com.example.hackathon.repository.DonacionRepository;
import com.example.hackathon.repository.UbicacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DonacionService {

    private final DonacionRepository repo;
    private final UbicacionRepository ubicacionRepository;

    public DonacionService(DonacionRepository repo, UbicacionRepository ubicacionRepository) {
        this.repo = repo;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Donacion> listar() {
        return repo.findAll();
    }

    public Optional<Donacion> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Donacion guardar(Donacion donacion) {
        validarCamposBasicos(donacion.getTitulo(), donacion.getDescripcion(), donacion.getCantidad());
        return repo.save(donacion);
    }

    public Donacion guardar(DonacionDTO donacionDto) {
        validarCamposBasicos(
                donacionDto.getTitulo(),
                donacionDto.getDescripcion(),
                donacionDto.getCantidad()
        );

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(donacionDto.getLatitud());
        ubicacion.setLongitud(donacionDto.getLongitud());
        ubicacion.setDireccion(
                donacionDto.getDireccion() == null || donacionDto.getDireccion().isBlank()
                        ? "Ubicacion registrada desde el mapa"
                        : donacionDto.getDireccion().trim()
        );
        ubicacion.setCiudad(
                donacionDto.getCiudad() == null || donacionDto.getCiudad().isBlank()
                        ? "Neiva"
                        : donacionDto.getCiudad().trim()
        );
        Ubicacion ubicacionGuardada = ubicacionRepository.save(ubicacion);

        TipoDonacion tipo = new TipoDonacion();
        tipo.setId(donacionDto.getTipoId());

        Donacion donacion = new Donacion();
        donacion.setTitulo(donacionDto.getTitulo().trim());
        donacion.setDescripcion(donacionDto.getDescripcion().trim());
        donacion.setCantidad(donacionDto.getCantidad());
        donacion.setTipo(tipo);
        donacion.setUbicacion(ubicacionGuardada);
        donacion.setEstado(EstadoDonacion.DISPONIBLE);

        return repo.save(donacion);
    }

    public Donacion actualizar(Long id, Donacion donacion) {
        Donacion existente = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Donacion con ID " + id + " no encontrada")
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
            throw new ResourceNotFoundException("Donacion con ID " + id + " no encontrada");
        }

        repo.deleteById(id);
    }

    private void validarCamposBasicos(String titulo, String descripcion, Integer cantidad) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
    }
}
