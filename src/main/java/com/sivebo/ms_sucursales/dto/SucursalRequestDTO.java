package com.sivebo.ms_sucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDTO {

        @NotBlank(message = "El nombre de la sucursal es obligatorio")
	String nombre;

	@NotNull(message = "El nombre de la comuna es obligatorio")
	String nombreComuna;

	@NotBlank(message = "La dirección física de la sucursal es obligatoria")
	String direccionFisica;

        @Min(value = 100000000, message = "El teléfono de contacto debe tener al menos 9 dígitos")
        @NotNull(message = "El teléfono de contacto de la sucursal es obligatorio")
	Integer telefonoContacto;
}
