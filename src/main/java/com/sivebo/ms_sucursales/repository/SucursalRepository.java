package com.sivebo.ms_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_sucursales.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
        List<Sucursal> findByIdComuna(Long id_comuna);
        Optional<Sucursal> findByNombre(String nombre);
        
}
