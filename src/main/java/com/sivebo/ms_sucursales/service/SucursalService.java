package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sivebo.ms_sucursales.dto.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

        private final SucursalRepository sucursalRepository;
        
        private final WebClient 

        private SucursalResponseDTO mapToDTO(Sucursal sucursal) {
                return new SucursalResponseDTO(
                        sucursal.getId(),
                        sucursal.getNombre(),   
                        sucursal.getIdComuna(),
                        sucursal.getDireccionFisica(),
                        sucursal.getTelefonoContacto()
                );
        }

        public List<SucursalResponseDTO> getAll() {
                return sucursalRepository.findAll()
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<SucursalResponseDTO> getById(Long id) {
                return sucursalRepository.findById(id).map(this::mapToDTO);
        }

        public List<SucursalResponseDTO> getByComunaId(Long idComuna) {
                return sucursalRepository.findByIdComuna(idComuna)
                        .stream().map(this::mapToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<SucursalResponseDTO> getByNombre(String nombre) {
                return sucursalRepository.findByNombre(nombre).map(this::mapToDTO);
        }

        public SucursalResponseDTO create(SucursalRequestDTO dto) {
                return mapToDTO(sucursalRepository.save(
                        new Sucursal(
                                null,
                                dto.getNombre(),
                                dto.getIdComuna(),
                                dto.getDireccionFisica(),
                                dto.getTelefonoContacto()
                        )
                ));
        }

        public Optional<SucursalResponseDTO> update(Long id, SucursalRequestDTO dto) {
                return sucursalRepository.findById(id).map(sucursal -> {
                        sucursal.setNombre(dto.getNombre());
                        sucursal.setIdComuna(dto.getIdComuna());
                        sucursal.setDireccionFisica(dto.getDireccionFisica());
                        sucursal.setTelefonoContacto(dto.getTelefonoContacto());
                        return mapToDTO(sucursalRepository.save(sucursal));
                });
        }

        public Boolean delete(Long id) {
                sucursalRepository.deleteById(id);
                return !sucursalRepository.existsById(id);
        }
}
