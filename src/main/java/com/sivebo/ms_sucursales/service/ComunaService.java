package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.utils.MapperUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComunaService {

        private final ComunaRepository comunaRepository;
        private final MapperUtil mapperUtil;

        public List<ComunaResponseDTO> getAll() {
                return comunaRepository.findAll()
                                .stream()
                                .map(mapperUtil::mapComunaToDTO)
                                .collect(Collectors.toList());
        }

        public Optional<ComunaResponseDTO> getById(Long id) {
                return comunaRepository.findById(id).map(mapperUtil::mapComunaToDTO);
        }

        public Optional<ComunaResponseDTO> getByNombre(String nombre) {
                return comunaRepository.findByNombre(nombre).map(mapperUtil::mapComunaToDTO);
        }

        public List<ComunaResponseDTO> getByRegionId(Long idRegion) {
                return comunaRepository.findByRegionId(idRegion)
                                .stream().map(mapperUtil::mapComunaToDTO)
                                .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> getByRegionNombre(String nombreRegion) {
                return comunaRepository.findByRegionNombre(nombreRegion)
                                .stream().map(mapperUtil::mapComunaToDTO)
                                .collect(Collectors.toList());
        }

        public List<ComunaResponseDTO> searchByAttribute(String id, String nombre, String region) {
                if (id != null) {
                        log.info(">>> Buscando comuna por id: {}", id);
                        return getById(Long.valueOf(id)).map(List::of).orElse(List.of());
                } else if (nombre != null) {
                        log.info(">>> Buscando comuna por nombre: {}", nombre);
                        return getByNombre(nombre).map(List::of).orElse(List.of());
                } else if (region != null) {
                        log.info(">>> Buscando comuna por region: {}", region);
                        return getByRegionNombre(region);
                }
                return getAll();
        }
}
