package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.ComunaResponseDTO;
import com.sivebo.ms_sucursales.dto.RegionResponseDTO;
import com.sivebo.ms_sucursales.dto.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.RegionRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

        private final SucursalRepository sucursalRepository;
        private final ComunaRepository comunaRepository;
        private final RegionRepository regionRepository;

        private SucursalResponseDTO mapToDTO(Sucursal sucursal) {
                return new SucursalResponseDTO(
                        sucursal.getId(),
                        sucursal.getNombre(),   
                        sucursal.getComuna().getNombre(),
                        sucursal.getComuna().getRegion().getNombre(),
                        sucursal.getDireccionFisica(),
                        sucursal.getTelefonoContacto()
                );
        }


        private ComunaResponseDTO mapToDTO(Comuna comuna) {
                return new ComunaResponseDTO(
                        comuna.getId(),
                        comuna.getNombre(),   
                        comuna.getRegion().getNombre()
                );
        }

        private RegionResponseDTO mapToDTO(Region region) {
                return new RegionResponseDTO(
                        region.getId(),
                        region.getNombre()
                );
        }
        
        public List<SucursalResponseDTO> getAllSucursales() {
                return sucursalRepository.findAll()
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> getAllComunas() {
                return comunaRepository.findAll()
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public List<RegionResponseDTO> getAllRegiones() {
                return regionRepository.findAll()
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<SucursalResponseDTO> getSucursalById(Long id) {
                return sucursalRepository.findById(id).map(this::mapToDTO);
        }

        public Optional<SucursalResponseDTO> getSucursalByNombre(String nombre) {
                return sucursalRepository.findByNombre(nombre).map(this::mapToDTO);
        }

        public List<SucursalResponseDTO> getSucursalByComunaId(Long idComuna) {
                return sucursalRepository.findByComunaId(idComuna)
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public List<SucursalResponseDTO> getSucursalbyComunaNombre(String nombreComuna) {
                return sucursalRepository.findByComunaNombre(nombreComuna)
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<ComunaResponseDTO> getComunaById(Long id) {
                return comunaRepository.findById(id).map(this::mapToDTO);
        }

        public Optional<ComunaResponseDTO> getComunaByNombre(String nombre) {
                return comunaRepository.findByNombre(nombre).map(this::mapToDTO);
        }

        public List<ComunaResponseDTO> getComunasByRegionId(Long idRegion) {
                return comunaRepository.findByRegionId(idRegion)
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> getComunasByRegionNombre(String nombreRegion) {
                return comunaRepository.findByRegionNombre(nombreRegion)
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<RegionResponseDTO> getRegionById(Long id) {
                return regionRepository.findById(id).map(this::mapToDTO);
        }
        
        public SucursalResponseDTO createSucursal(SucursalRequestDTO dto) {
                Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                    .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
                
                return mapToDTO(sucursalRepository.save(
                        new Sucursal(
                                null,
                                dto.getNombre(),
                                comuna,
                                dto.getDireccionFisica(),
                                dto.getTelefonoContacto()
                        )
                ));
        }

        public Optional<SucursalResponseDTO> updateSucursal(Long id, SucursalRequestDTO dto) {
                return sucursalRepository.findById(id).map(sucursal -> {
                Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                    .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
                        sucursal.setNombre(dto.getNombre());
                        sucursal.setComuna(comuna);
                        sucursal.setDireccionFisica(dto.getDireccionFisica());
                        sucursal.setTelefonoContacto(dto.getTelefonoContacto());
                        return mapToDTO(sucursalRepository.save(sucursal));
                });
        }

        public Boolean deleteSucursalById(Long id) {
                sucursalRepository.deleteById(id);
                return !sucursalRepository.existsById(id);
        }

        public Boolean deleteSucursalByNombre(String nombre) {
                sucursalRepository.deleteByNombre(nombre);
                return !sucursalRepository.existsByNombre(nombre);
        }
}
