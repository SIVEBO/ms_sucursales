package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.request.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.exception.EntityNotFoundException;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;
import com.sivebo.ms_sucursales.utils.MapToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService extends MapToDTO {

        private final SucursalRepository sucursalRepository;
        private final ComunaRepository comunaRepository;
        
        public List<SucursalResponseDTO> getAll() {
                return sucursalRepository.findAll()
                        .stream()
                        .map(this::mapSucursalToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<SucursalResponseDTO> getById(Long id) {
                return sucursalRepository.findById(id).map(this::mapSucursalToDTO);
        }

        public Optional<SucursalResponseDTO> getByNombre(String nombre) {
                return sucursalRepository.findByNombre(nombre).map(this::mapSucursalToDTO);
        }

        public List<SucursalResponseDTO> getByComunaId(Long idComuna) {
                return sucursalRepository.findByComunaId(idComuna)
                        .stream().map(this::mapSucursalToDTO)
                        .collect(Collectors.toList());
        }

        public List<SucursalResponseDTO> getByComunaNombre(String nombreComuna) {
                return sucursalRepository.findByComunaNombre(nombreComuna)
                        .stream().map(this::mapSucursalToDTO)
                        .collect(Collectors.toList());
        }

        public List<SucursalResponseDTO> getByRegionNombre(String nombreComuna) {
                return sucursalRepository.findByRegionNombre(nombreComuna)
                        .stream().map(this::mapSucursalToDTO)
                        .collect(Collectors.toList());
        }

        public SucursalResponseDTO create(SucursalRequestDTO dto) {
                Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                    .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada"));
                
                return mapSucursalToDTO(sucursalRepository.save(
                        new Sucursal(
                                null,
                                dto.getNombre(),
                                comuna,
                                dto.getDireccionFisica(),
                                dto.getTelefonoContacto()
                        )
                ));
        }

        public Optional<SucursalResponseDTO> update(Long id, SucursalRequestDTO dto) {
                return sucursalRepository.findById(id).map(sucursal -> {
                Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                    .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada"));
                        sucursal.setNombre(dto.getNombre());
                        sucursal.setComuna(comuna);
                        sucursal.setDireccionFisica(dto.getDireccionFisica());
                        sucursal.setTelefonoContacto(dto.getTelefonoContacto());
                        return mapSucursalToDTO(sucursalRepository.save(sucursal));
                });
        }

        public Boolean deleteById(Long id) {
                sucursalRepository.deleteById(id);
                return !sucursalRepository.existsById(id);
        }

        public Boolean deleteByNombre(String nombre) {
                sucursalRepository.deleteByNombre(nombre);
                return !sucursalRepository.existsByNombre(nombre);
        }
}
