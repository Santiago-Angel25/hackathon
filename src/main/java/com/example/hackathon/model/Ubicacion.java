package com.example.hackathon.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ubicaciones")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String ciudad;
    private double latitud;
    private double longitud;
}
