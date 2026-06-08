package com.sivebo.ms_sucursales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sivebo.ms_sucursales.model.Comuna;
import java.util.List;

public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    List<Comuna> findByRegionId(Long regionId);
}
