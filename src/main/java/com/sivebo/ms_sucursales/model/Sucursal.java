package com.sivebo.ms_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursales")
public class Sucursal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "nombre_sucursal", nullable = false, length = 30, unique = true)
	String nombre;

	@ManyToOne
	@JoinColumn(name = "id_comuna", nullable = false)
	Comuna comuna;

	@Column(name = "direccion_fisica", nullable = false, length = 100, unique = true)
	String direccionFisica;

	@Column(name = "telefono_contacto", unique = true)
	String telefonoContacto;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	EstadoSucursal estado = EstadoSucursal.ACTIVA;
}
