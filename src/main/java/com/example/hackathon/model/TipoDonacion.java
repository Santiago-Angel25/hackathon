package com.example.hackathon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_donacion")
public class TipoDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
