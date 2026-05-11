package com.sivebo.ms_sucursales.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

        private final SucursalRepository sucursalRepository;

        @Override
        public void run(String... args){
                if(sucursalRepository.count() > 0) {
                        log.info(">>> Sucursales ya cargadas. Se omite inicialización.");
                        return;
                }
                log.info(">>> Cargando sucursales iniciales...");
                sucursalRepository.save(new Sucursal(
                        null,
                        "Sucursal Centro",
                        1L,
                        "Av. Central 123",
                        123456789
                ));
                sucursalRepository.save(new Sucursal(
                        null,
                        "Sucursal Norte",
                        2L,
                        "Calle Norte 456",
                        987654321
                ));
                log.info(">>> Sucursales iniciales cargadas exitosamente.");
        }
        
}
