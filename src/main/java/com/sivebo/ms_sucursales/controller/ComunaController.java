package com.sivebo.ms_sucursales.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/comunas")
@RequiredArgsConstructor
public class ComunaController {

    private final ComunaService comunaService;

    @Operation(summary = "Obtener todas las comunas registradas", description = "Obtiene una lista de JSON de todas las comunas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunas obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComunaResponseDTO.class)))
    })
    @GetMapping
    public List<ComunaResponseDTO> getAll() {
        return comunaService.getAll();
    }

    @Operation(summary = "Obtener todas las comunas registradas por query", description = "Obtiene una lista o unidad de JSON de todas las comunas por query 'search?id=*', 'search?nombre=*' o 'search?region=*'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunas obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComunaResponseDTO.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> getByAtribute(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String region) {

        List<String> params = new ArrayList<>(Arrays.asList(id, nombre, region));

        int num_null = 0;
        for (String value : params) {
            if (value == null)
                num_null++;
        }
        int num_valid_params = params.size() - num_null;
        if (num_valid_params != 1) {
            log.info(" Solo se permite un atributo de búsqueda a la vez pero ingresado {}", num_valid_params);
            return ResponseEntity.badRequest().body(
                    "Solo se permite un atributo de búsqueda a la vez pero ingresado " + num_valid_params);
        } else if (id != null) {
            log.info(">>> Buscando sucursal por id: {}", id);
            return comunaService.getById(Long.valueOf(id))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (nombre != null) {
            log.info(">>> Buscando comuna por nombre: {}", nombre);
            return comunaService.getByNombre(nombre)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (region != null) {
            log.info(">>> Buscando comuna por region: {}", region);
            return ResponseEntity.ok(comunaService.getByRegionNombre(region));
        }
        return ResponseEntity.internalServerError().body("Error en el URL query");
    }

}