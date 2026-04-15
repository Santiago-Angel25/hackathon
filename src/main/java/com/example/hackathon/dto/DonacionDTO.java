package com.example.hackathon.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonacionDTO {

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @NotNull(message = "El ID del tipo es requerido")
    private Long tipoId;

    @NotNull(message = "El ID del donador es requerido")
    private Long donadorId;

    @NotNull(message = "El ID de la ubicación es requerido")
    private Long ubicacionId;
}

