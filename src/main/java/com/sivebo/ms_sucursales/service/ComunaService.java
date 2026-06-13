package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.ComunaResponseDTO;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.utils.MapToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComunaService extends MapToDTO {

        private final ComunaRepository comunaRepository;

        public List<ComunaResponseDTO> getAllComunas() {
                return comunaRepository.findAll()
                        .stream()
                        .map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<ComunaResponseDTO> getComunaById(Long id) {
                return comunaRepository.findById(id).map(this::mapComunaToDTO);
        }

        public Optional<ComunaResponseDTO> getComunaByNombre(String nombre) {
                return comunaRepository.findByNombre(nombre).map(this::mapComunaToDTO);
        }

        public List<ComunaResponseDTO> getComunasByRegionId(Long idRegion) {
                return comunaRepository.findByRegionId(idRegion)
                        .stream().map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> getComunasByRegionNombre(String nombreRegion) {
                return comunaRepository.findByRegionNombre(nombreRegion)
                        .stream().map(this::mapComunaToDTO)
                        .collect(Collectors.toList());
        }

}
