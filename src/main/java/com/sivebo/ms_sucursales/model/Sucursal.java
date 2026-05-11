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
@Table(name = "sucursales")
public class Sucursal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	@Column(name = "nombre", nullable = false, length = 30)
	String nombre;

	@Column(name = "id_comuna", nullable = false)
	Long idComuna;

	@Column(name = "direccion_fisica", nullable = false, length = 100)
	String direccionFisica;

	@Column(name = "telefono_contacto", nullable = false)
	Integer telefonoContacto;
}
