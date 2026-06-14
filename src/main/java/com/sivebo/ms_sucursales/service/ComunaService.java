package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.utils.MapToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComunaService extends MapToDTO {

        private final ComunaRepository comunaRepository;

        public List<ComunaResponseDTO> getAll() {
                return comunaRepository.findAll()
                        .stream()
                        .map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<ComunaResponseDTO> getById(Long id) {
                return comunaRepository.findById(id).map(this::mapComunaToDTO);
        }

        public Optional<ComunaResponseDTO> getByNombre(String nombre) {
                return comunaRepository.findByNombre(nombre).map(this::mapComunaToDTO);
        }

        public List<ComunaResponseDTO> getByRegionId(Long idRegion) {
                return comunaRepository.findByRegionId(idRegion)
                        .stream().map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> getByRegionNombre(String nombreRegion) {
                return comunaRepository.findByRegionNombre(nombreRegion)
                        .stream().map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

}
