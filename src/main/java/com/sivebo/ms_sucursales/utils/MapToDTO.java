
package com.sivebo.ms_sucursales.utils;

import com.sivebo.ms_sucursales.dto.ComunaResponseDTO;
import com.sivebo.ms_sucursales.dto.RegionResponseDTO;
import com.sivebo.ms_sucursales.dto.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;

public class MapToDTO {
        protected SucursalResponseDTO mapSucursalToDTO(Sucursal sucursal) {
                return new SucursalResponseDTO(
                                sucursal.getId(),
                                sucursal.getNombre(),
                                sucursal.getComuna().getNombre(),
                                sucursal.getComuna().getRegion().getNombre(),
                                sucursal.getDireccionFisica(),
                                sucursal.getTelefonoContacto());
        }

        protected ComunaResponseDTO mapComunaToDTO(Comuna comuna) {
                return new ComunaResponseDTO(
                                comuna.getId(),
                                comuna.getNombre(),
                                comuna.getRegion().getNombre());
        }

        protected RegionResponseDTO mapRegionToDTO(Region region) {
                return new RegionResponseDTO(
                                region.getId(),
                                region.getNombre());
        }
}