package com.example.hackathon.repository;

import com.example.hackathon.model.Donacion;
import com.example.hackathon.model.EstadoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByEstadoNot(EstadoDonacion estado);
    List<Donacion> findByDonadorEmailIgnoreCaseAndEstadoNot(String email, EstadoDonacion estado);
    long countByEstado(EstadoDonacion estado);
    long countByEstadoNot(EstadoDonacion estado);
}
