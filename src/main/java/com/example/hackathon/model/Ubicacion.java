package com.example.hackathon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ubicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;

    @NotNull(message = "La latitud es requerida")
    @DecimalMin(value = "-90", message = "Latitud debe estar entre -90 y 90")
    @DecimalMax(value = "90", message = "Latitud debe estar entre -90 y 90")
    private double latitud;

    @NotNull(message = "La longitud es requerida")
    @DecimalMin(value = "-180", message = "Longitud debe estar entre -180 y 180")
    @DecimalMax(value = "180", message = "Longitud debe estar entre -180 y 180")
    private double longitud;
}
