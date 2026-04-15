package com.example.hackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    // 🔥 RELACIONES

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    @NotNull(message = "El tipo de donación es requerido")
    private TipoDonacion tipo;

    @ManyToOne
    @JoinColumn(name = "id_donador")
    @NotNull(message = "El donador es requerido")
    private Usuario donador;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    @NotNull(message = "La ubicación es requerida")
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    private EstadoDonacion estado;
}
