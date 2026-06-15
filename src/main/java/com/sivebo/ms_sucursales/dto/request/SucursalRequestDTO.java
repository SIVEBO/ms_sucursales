package com.sivebo.ms_sucursales.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDTO {

	@NotBlank(message = "El nombre de la sucursal es obligatorio")
	String nombre;

	@NotBlank(message = "El nombre de la comuna es obligatorio")
	String nombreComuna;

	@NotBlank(message = "La dirección física de la sucursal es obligatoria")
	String direccionFisica;

	String telefonoContacto;
}
