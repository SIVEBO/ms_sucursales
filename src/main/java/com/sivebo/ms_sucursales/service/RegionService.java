package com.sivebo.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sivebo.ms_sucursales.dto.RegionResponseDTO;
import com.sivebo.ms_sucursales.repository.RegionRepository;
import com.sivebo.ms_sucursales.utils.MapToDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService extends MapToDTO {
    
        private final RegionRepository regionRepository;

        public List<RegionResponseDTO> getAllRegiones() {
                return regionRepository.findAll()
                        .stream()
                        .map(this::mapRegionToDTO)
                        .collect(Collectors.toList());
        }

        public Optional<RegionResponseDTO> getRegionById(Long id) {
                return regionRepository.findById(id).map(this::mapRegionToDTO);
        }
}