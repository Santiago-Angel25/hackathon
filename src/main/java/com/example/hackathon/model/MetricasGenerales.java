package com.example.hackathon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "metricas_generales")
public class MetricasGenerales {

    @Id
    private Long id;

    private Long entregasRealizadas;
    private Long vidasImpactadas;
}
