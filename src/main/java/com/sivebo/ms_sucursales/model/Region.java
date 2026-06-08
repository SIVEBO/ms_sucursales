package com.sivebo.ms_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regiones")
public class Region {
        

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        Long id;

        @Column(name = "nombre_region", nullable = false, length=30, unique = true)
        String nombre;
}
