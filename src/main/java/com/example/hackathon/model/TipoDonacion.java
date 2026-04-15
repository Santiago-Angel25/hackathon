package com.example.hackathon.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipos_donacion")
public class TipoDonacion {



    @Id
    private Long id;
    @Column(name = "id_tipo")
    private Integer idTipo;

    @Column(name = "id_donador")
    private Integer idDonador;

    @Column(name = "id_ubicacion")
    private Integer idUbicacion;
}
