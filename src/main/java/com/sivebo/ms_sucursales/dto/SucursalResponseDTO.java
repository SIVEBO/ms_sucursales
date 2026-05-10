package com.sivebo.ms_sucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO {
        
        Long id_sucursal;
        String nombre;
        Long id_comuna;
        String direccion_fisica;
        Integer telefono_contacto;
}
