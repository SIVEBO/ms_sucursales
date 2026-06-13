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
                        return;
                }
                log.info(">>> Cargando regiones iniciales...");

                Region regionMetropolitana = new Region(null, "regionMetropolitana");
                Region valparaiso = new Region(null, "Valparaiso");

                regionRepository.save(regionMetropolitana);
                regionRepository.save(valparaiso);

                log.info(">>> Regiones iniciales cargadas exitosamente.");

                if (comunaRepository.count() > 0) {
                        log.info(">>> comunas ya cargadas. Se omite inicialización.");
                        return;
                }
                log.info(">>> Cargando comunas iniciales...");

                Comuna comunaSantiago = new Comuna(null, "Santiago", regionMetropolitana);
                Comuna comunaProvidencia = new Comuna(null, "Providencia", regionMetropolitana);

                comunaRepository.save(comunaSantiago);
                comunaRepository.save(comunaProvidencia);

                log.info(">>> Comunas iniciales cargadas exitosamente.");

                if (sucursalRepository.count() > 0) {
                        log.info(">>> Sucursales ya cargadas. Se omite inicialización.");
                        return;
                }
                log.info(">>> Cargando sucursales iniciales...");

                Sucursal sucursalPlazaDeArmas = new Sucursal(
                                null,
                                "Plaza de Armas",
                                comunaSantiago,
                                "Catedral 989",
                                123456789);

                Sucursal sucursalPanoramico = new Sucursal(
                                null,
                                "Panoramico",
                                comunaProvidencia,
                                "Av. Nueva Providencia 2092",
                                123456789);

                sucursalRepository.save(sucursalPlazaDeArmas);
                sucursalRepository.save(sucursalPanoramico);

                log.info(">>> Sucursales iniciales cargadas exitosamente.");
        }

}
