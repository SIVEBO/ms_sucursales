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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/comunas")
@RequiredArgsConstructor
@Tag(name = "Comunas", description = "Operaciones relacionadas con las comunas")
public class ComunaController {

        private final ComunaService comunaService;

        @Operation(
                summary = "Obtener todas las comunas registradas",
                description = "Obtiene una lista de JSON de todas las comunas"
        )
        @ApiResponses(value = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Comunas obtenidas exitosamente",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ComunaResponseDTO.class)
                                )
                        )
        })
        @GetMapping
        public List<ComunaResponseDTO> getAll() {
                return comunaService.getAll();
        }

        @Operation(
                summary = "Obtener comunas por query",
                description = "Obtiene una lista o unidad de JSON de todas las comunas por query 'buscar?id=*', 'buscar?nombre=*' o 'buscar?region=*'"
        )
        @ApiResponses(value = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Comunas obtenidas exitosamente",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ComunaResponseDTO.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Se proporcionó un número de parámetros inválido",
                                content = @Content(mediaType = "application/json")
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "No se encontraron comunas con los criterios indicados",
                                content = @Content(mediaType = "application/json")
                        )
        })
        @GetMapping("/buscar")
        public ResponseEntity<?> getByAtribute(
                        @RequestParam(required = false) String id,
                        @RequestParam(required = false) String nombre,
                        @RequestParam(required = false) String region) {

                List<String> params = new ArrayList<>(Arrays.asList(id, nombre, region));

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
                }

                List<ComunaResponseDTO> results = comunaService.searchByAttribute(id, nombre, region);

                if (results.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }

                if (id != null || nombre != null) {
                        return ResponseEntity.ok(results.get(0));
                }

                return ResponseEntity.ok(results);
        }

}
