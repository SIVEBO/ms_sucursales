package com.sivebo.ms_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_sucursales.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

        List<Sucursal> findByComunaId(Long comunaId);

        List<Sucursal> findByComunaNombre(String comunaNombre);

        Optional<Sucursal> findByNombre(String nombre);

        void deleteByNombre(String nombre);

        Boolean existsByNombre(String nombre);
        
}
