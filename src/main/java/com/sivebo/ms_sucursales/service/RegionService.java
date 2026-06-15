package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.response.RegionResponseDTO;
import com.sivebo.ms_sucursales.repository.RegionRepository;
import com.sivebo.ms_sucursales.utils.MapperUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService {

        private final RegionRepository regionRepository;
        private final MapperUtil mapperUtil;

        public List<RegionResponseDTO> getAll() {
                return regionRepository.findAll()
                                .stream()
                                .map(mapperUtil::mapRegionToDTO)
                                .collect(Collectors.toList());
        }

        public Optional<RegionResponseDTO> getById(Long id) {
                return regionRepository.findById(id).map(mapperUtil::mapRegionToDTO);
        }

        public Optional<RegionResponseDTO> getByNombre(String nombre) {
                return regionRepository.findByNombre(nombre).map(mapperUtil::mapRegionToDTO);
        }
}