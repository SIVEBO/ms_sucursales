package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivebo.ms_sucursales.dto.request.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.exception.EntityNotFoundException;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.EstadoSucursal;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;
import com.sivebo.ms_sucursales.utils.MapperUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

        private final SucursalRepository sucursalRepository;
        private final ComunaRepository comunaRepository;
        private final MapperUtil mapperUtil;

        @Transactional(readOnly = true)
        public List<SucursalResponseDTO> getAll() {
                return sucursalRepository.findByEstado(EstadoSucursal.ACTIVA)
                                .stream()
                                .map(mapperUtil::mapSucursalToDTO)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public Optional<SucursalResponseDTO> getById(Long id) {
                return sucursalRepository.findById(id).map(mapperUtil::mapSucursalToDTO);
        }

        @Transactional(readOnly = true)
        public Optional<SucursalResponseDTO> getByNombre(String nombre) {
                return sucursalRepository.findByNombre(nombre).map(mapperUtil::mapSucursalToDTO);
        }

        @Transactional(readOnly = true)
        public List<SucursalResponseDTO> getByComunaNombre(String nombreComuna) {
                return sucursalRepository.findByComunaNombreAndEstado(nombreComuna, EstadoSucursal.ACTIVA)
                                .stream().map(mapperUtil::mapSucursalToDTO)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<SucursalResponseDTO> getByRegionNombre(String nombreRegion) {
                return sucursalRepository.findByRegionNombreAndEstado(nombreRegion, EstadoSucursal.ACTIVA)
                                .stream().map(mapperUtil::mapSucursalToDTO)
                                .collect(Collectors.toList());
        }

        @Transactional
        public SucursalResponseDTO create(SucursalRequestDTO dto) {
                Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                                .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada"));

                return mapperUtil.mapSucursalToDTO(sucursalRepository.save(
                                new Sucursal(
                                                null,
                                                dto.getNombre(),
                                                comuna,
                                                dto.getDireccionFisica(),
                                                dto.getTelefonoContacto(),
                                                EstadoSucursal.ACTIVA)));
        }

        @Transactional
        public Optional<SucursalResponseDTO> update(Long id, SucursalRequestDTO dto) {
                return sucursalRepository.findById(id).map(sucursal -> {
                        Comuna comuna = comunaRepository.findByNombre(dto.getNombreComuna())
                                        .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada"));
                        sucursal.setNombre(dto.getNombre());
                        sucursal.setComuna(comuna);
                        sucursal.setDireccionFisica(dto.getDireccionFisica());
                        sucursal.setTelefonoContacto(dto.getTelefonoContacto());
                        return mapperUtil.mapSucursalToDTO(sucursalRepository.save(sucursal));
                });
        }

        @Transactional
        public Optional<SucursalResponseDTO> deactivate(Long id) {
                return sucursalRepository.findById(id).map(sucursal -> {
                        sucursal.setEstado(EstadoSucursal.INACTIVA);
                        return mapperUtil.mapSucursalToDTO(sucursalRepository.save(sucursal));
                });
        }

        @Transactional
        public Optional<SucursalResponseDTO> activate(Long id) {
                return sucursalRepository.findById(id).map(sucursal -> {
                        sucursal.setEstado(EstadoSucursal.ACTIVA);
                        return mapperUtil.mapSucursalToDTO(sucursalRepository.save(sucursal));
                });
        }

        @Transactional
        public Boolean deleteByNombre(String nombre) {
                sucursalRepository.deleteByNombre(nombre);
                return !sucursalRepository.existsByNombre(nombre);
        }

        public List<SucursalResponseDTO> searchByAttribute(String id, String nombre, String comuna, String region) {
                if (id != null) {
                        log.info(">>> Buscando sucursal por id: {}", id);
                        return getById(Long.valueOf(id)).map(List::of).orElse(List.of());
                } else if (nombre != null) {
                        log.info(">>> Buscando sucursal por nombre: {}", nombre);
                        return getByNombre(nombre).map(List::of).orElse(List.of());
                } else if (comuna != null) {
                        log.info(">>> Buscando sucursal por comuna: {}", comuna);
                        return getByComunaNombre(comuna);
                } else if (region != null) {
                        log.info(">>> Buscando sucursal por región: {}", region);
                        return getByRegionNombre(region);
                }
                return getAll();
        }
}
