package com.sivebo.ms_sucursales.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO {

        Long id;
        String nombre;
        String nombreComuna;
        String nombreRegion;
        String direccionFisica;
        String telefonoContacto;
}
