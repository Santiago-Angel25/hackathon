package com.example.hackathon.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descripcion;
    private double lat;
    private double lng;

}