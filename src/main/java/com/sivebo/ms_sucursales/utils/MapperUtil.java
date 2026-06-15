package com.sivebo.ms_sucursales.utils;

import org.springframework.stereotype.Component;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.dto.response.RegionResponseDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;

@Component
public class MapperUtil {

        public SucursalResponseDTO mapSucursalToDTO(Sucursal sucursal) {
                return new SucursalResponseDTO(
                                sucursal.getId(),
                                sucursal.getNombre(),
                                sucursal.getComuna().getNombre(),
                                sucursal.getComuna().getRegion().getNombre(),
                                sucursal.getDireccionFisica(),
                                sucursal.getTelefonoContacto());
        }

        public ComunaResponseDTO mapComunaToDTO(Comuna comuna) {
                return new ComunaResponseDTO(
                                comuna.getId(),
                                comuna.getNombre(),
                                comuna.getRegion().getNombre());
        }

        public RegionResponseDTO mapRegionToDTO(Region region) {
                return new RegionResponseDTO(
                                region.getId(),
                                region.getNombre());
        }
}
