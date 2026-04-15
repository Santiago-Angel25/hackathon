package com.example.hackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 150, message = "El título debe tener entre 3 y 150 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    // 🔥 RELACIONES (solo validamos que existan)
    @NotNull(message = "Debe seleccionar un tipo")
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoDonacion tipo;

    @NotNull(message = "Debe haber un donador")
    @ManyToOne
    @JoinColumn(name = "id_donador")
    private Usuario donador;

    @NotNull(message = "Debe haber una ubicación")
    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    private EstadoDonacion estado;
}
