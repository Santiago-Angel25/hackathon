package com.example.hackathon.repository;

import com.example.hackathon.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
}
