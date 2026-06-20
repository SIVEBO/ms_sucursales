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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/regiones")
@RequiredArgsConstructor
@Tag(name = "Regiones", description = "Operaciones relacionadas con las regiones")
public class RegionController {

        private final RegionService regionService;

        @Operation(
                summary = "Obtener todas las regiones registradas",
                description = "Obtiene una lista de JSON de todas las regiones"
        )
        @ApiResponses(value = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Regiones obtenidas exitosamente",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = RegionResponseDTO.class)
                                )
                        )
        })
        @GetMapping
        public List<RegionResponseDTO> getAll() {
                return regionService.getAll();
        }

        @Operation(
                summary = "Obtener regiones por query",
                description = "Obtiene una región por query 'buscar?id=*' o 'buscar?nombre=*'"
        )
        @ApiResponses(value = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Región obtenida exitosamente",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = RegionResponseDTO.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Se proporcionó un número de parámetros inválido",
                                content = @Content(mediaType = "application/json")
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "No se encontró región con los criterios indicados",
                                content = @Content(mediaType = "application/json")
                        )
        })
        @GetMapping("/buscar")
        public ResponseEntity<?> getByAtribute(
                        @RequestParam(required = false) String id,
                        @RequestParam(required = false) String nombre) {

                List<String> params = new ArrayList<>(Arrays.asList(id, nombre));

                int numNull = 0;
                for (String value : params) {
                        if (value == null)
                                numNull++;
                }
                int numValidParams = params.size() - numNull;
                if (numValidParams != 1) {
                        log.info("Solo se permite un atributo de búsqueda a la vez pero ingresado {}",
                                        numValidParams);
                        return ResponseEntity.badRequest().body(
                                        "Solo se permite un atributo de búsqueda a la vez pero ingresado "
                                                        + numValidParams);
                } else if (id != null) {
                        log.info(">>> Buscando region por id: {}", id);
                        return regionService.getById(Long.valueOf(id))
                                        .map(ResponseEntity::ok)
                                        .orElse(ResponseEntity.notFound().build());
                } else if (nombre != null) {
                        log.info(">>> Buscando region por nombre: {}", nombre);
                        return regionService.getByNombre(nombre)
                                        .map(ResponseEntity::ok)
                                        .orElse(ResponseEntity.notFound().build());
                }
                return ResponseEntity.internalServerError().body("Error en el URL query");
        }

}
