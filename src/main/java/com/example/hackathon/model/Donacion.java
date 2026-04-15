package com.example.hackathon.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private Integer cantidad;

    // 🔥 RELACIONES

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoDonacion tipo;

    @ManyToOne
    @JoinColumn(name = "id_donador")
    private Usuario donador;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;
    @Enumerated(EnumType.STRING)
    private EstadoDonacion estado;
}
