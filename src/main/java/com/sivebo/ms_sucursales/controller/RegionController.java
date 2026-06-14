package com.sivebo.ms_sucursales.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.dto.response.RegionResponseDTO;
import com.sivebo.ms_sucursales.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/regiones")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "Obtener todas las regiones registradas", description = "Obtiene una lista de JSON de todas las comunas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "regiones obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class)))
    })
    @GetMapping
    public List<RegionResponseDTO> getAll() {
        return regionService.getAll();
    }

    @Operation(summary = "Obtener todas las regiones registradas por query", description = "Obtiene una lista o unidad de JSON de todas las regiones por query 'search?nombre=*' o 'search?nombreRegion=*'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "regiones obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> getByAtribute(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String nombre) {

        List<String> params = new ArrayList<>(Arrays.asList(id, nombre));

        int num_null = 0;
        for (String value : params) {
            if (value == null)
                num_null++;
        }
        if (num_null != 1) {
            log.info(" Solo se permite un atributo de búsqueda a la vez pero ingresado {}", (params.size() - num_null));
            return ResponseEntity.badRequest().body(
                    "Solo se permite un atributo de búsqueda a la vez pero ingresado " + (params.size() - num_null));
        } else if (id != null) {
            log.info(">>> Buscando sucursal por id: {}", id);
            return regionService.getById(Long.valueOf(id))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (nombre != null) {
            log.info(">>> Buscando comuna por nombre: {}", nombre);
            return regionService.getByNombre(nombre)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.internalServerError().body("Error en el URL query");
    }

}