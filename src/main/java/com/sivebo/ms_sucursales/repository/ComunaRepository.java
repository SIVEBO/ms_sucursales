package com.sivebo.ms_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_sucursales.model.Comuna;

public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    List<Comuna> findByRegionId(Long regionId);

    List<Comuna> findByRegionNombre(String regionNombre);

    Optional<Comuna> findByNombre(String nombre);
}
