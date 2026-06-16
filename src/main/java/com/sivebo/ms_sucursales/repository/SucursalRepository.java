package com.sivebo.ms_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sivebo.ms_sucursales.model.EstadoSucursal;
import com.sivebo.ms_sucursales.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

        List<Sucursal> findByComunaId(Long comunaId);

        List<Sucursal> findByComunaNombre(String comunaNombre);

        List<Sucursal> findByEstado(EstadoSucursal estado);

        List<Sucursal> findByComunaNombreAndEstado(String comunaNombre, EstadoSucursal estado);

        @Query("SELECT s FROM Sucursal s JOIN s.comuna c JOIN c.region r WHERE r.nombre = :regionNombre")
        List<Sucursal> findByRegionNombre(@Param("regionNombre") String regionNombre);

        @Query("SELECT s FROM Sucursal s JOIN s.comuna c JOIN c.region r WHERE r.nombre = :regionNombre AND s.estado = :estado")
        List<Sucursal> findByRegionNombreAndEstado(@Param("regionNombre") String regionNombre, @Param("estado") EstadoSucursal estado);

        Optional<Sucursal> findByNombre(String nombre);

        void deleteByNombre(String nombre);

        Boolean existsByNombre(String nombre);

}
