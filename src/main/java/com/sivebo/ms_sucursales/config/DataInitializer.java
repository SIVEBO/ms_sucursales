package com.sivebo.ms_sucursales.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.repository.SucursalRepository;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.RegionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final SucursalRepository sucursalRepository;
        private final ComunaRepository comunaRepository;
        private final RegionRepository regionRepository;

        @Override
        public void run(String... args) {
                if (regionRepository.count() > 0) {
                        log.info(">>> regiones ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando regiones iniciales...");

                        Region regionMetropolitana = new Region(null, "Metropolitana");
                        Region regionValparaiso = new Region(null, "Valparaiso");

                        regionRepository.save(regionMetropolitana);
                        regionRepository.save(regionValparaiso);

                        log.info(">>> Regiones iniciales cargadas exitosamente.");
                }

                if (comunaRepository.count() > 0) {
                        log.info(">>> comunas ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando comunas iniciales...");

                        Comuna comunaSantiago = new Comuna(null,
                                        "Santiago",
                                        regionRepository
                                                        .findByNombre("Metropolitana")
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Region no encontrada")));
                        Comuna comunaProvidencia = new Comuna(null,
                                        "Providencia",
                                        regionRepository
                                                        .findByNombre("Metropolitana")
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Region no encontrada")));

                        comunaRepository.save(comunaSantiago);
                        comunaRepository.save(comunaProvidencia);

                        log.info(">>> Comunas iniciales cargadas exitosamente.");
                }

                if (sucursalRepository.count() > 0) {
                        log.info(">>> Sucursales ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando sucursales iniciales...");

                        Sucursal sucursalPlazaDeArmas = new Sucursal(
                                        null,
                                        "Plaza de Armas",
                                        comunaRepository
                                                        .findByNombre("Santiago")
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Region no encontrada")),
                                        "Catedral 989",
                                        "+56992315785");

                        Sucursal sucursalPanoramico = new Sucursal(
                                        null,
                                        "Panoramico",
                                        comunaRepository
                                                        .findByNombre("Santiago")
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Region no encontrada")),
                                        "Av. Nueva Providencia 2092",
                                        "+56991234567");

                        sucursalRepository.save(sucursalPlazaDeArmas);
                        sucursalRepository.save(sucursalPanoramico);

                        log.info(">>> Sucursales iniciales cargadas exitosamente.");
                }

                log.info(">>> Inicialización de datos completada.");
        }

}
