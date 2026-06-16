package com.sivebo.ms_sucursales.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.dto.request.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/sucursales")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales")
public class SucursalController {

        private final SucursalService sucursalService;

        @Operation(summary = "Obtener sucursales por query", description = "Obtiene una lista o unidad de JSON de sucursales activas por query 'buscar?id=*', 'buscar?nombre=*', 'buscar?comuna=*' o 'buscar?region=*'")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sucursales obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Se proporcionó un número de parámetros inválido", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "No se encontraron sucursales con los criterios indicados", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/buscar")
        public ResponseEntity<?> getByAtribute(
                        @RequestParam(required = false) String id,
                        @RequestParam(required = false) String nombre,
                        @RequestParam(required = false) String comuna,
                        @RequestParam(required = false) String region) {

                List<String> params = new ArrayList<>(Arrays.asList(id, nombre, comuna, region));

                int num_null = 0;
                for (String value : params) {
                        if (value == null)
                                num_null++;
                }
                int num_valid_params = params.size() - num_null;
                if (num_valid_params != 1) {
                        log.info("Solo se permite un atributo de búsqueda a la vez pero ingresado {}",
                                        num_valid_params);
                        return ResponseEntity.badRequest()
                                        .body("Solo se permite un atributo de búsqueda a la vez pero ingresado "
                                                        + num_valid_params);
                }

                List<SucursalResponseDTO> results = sucursalService.searchByAttribute(id, nombre, comuna, region);

                if (results.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }

                if (id != null || nombre != null) {
                        return ResponseEntity.ok(results.get(0));
                }

                return ResponseEntity.ok(results);
        }

        @Operation(summary = "Obtener todas las sucursales registradas", description = "Obtiene una lista de todas las sucursales")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sucursales obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class)))
        })
        @GetMapping
        public List<SucursalResponseDTO> getAll() {
                return sucursalService.getAll();
        }

        @Operation(summary = "Crear una nueva sucursal", description = "Registra una nueva sucursal asociada a una comuna existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos o comuna no encontrada", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "409", description = "Ya existe una sucursal con ese nombre o dirección", content = @Content(mediaType = "application/json"))
        })
        @PostMapping
        public ResponseEntity<SucursalResponseDTO> create(@Valid @RequestBody SucursalRequestDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(sucursalService.create(dto));
        }

        @Operation(summary = "Actualizar una sucursal", description = "Modifica los datos de una sucursal existente por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos o comuna no encontrada", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content(mediaType = "application/json"))
        })
        @PutMapping("/{id}")
        public ResponseEntity<SucursalResponseDTO> update(
                        @PathVariable Long id,
                        @Valid @RequestBody SucursalRequestDTO dto) {

                return sucursalService.update(id, dto)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Desactivar una sucursal", description = "Cambia el estado de una sucursal a INACTIVA sin eliminarla")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sucursal desactivada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}/desactivar")
        public ResponseEntity<SucursalResponseDTO> deactivate(@PathVariable Long id) {
                return sucursalService.deactivate(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Eliminar una sucursal", description = "Elimina permanentemente una sucursal por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteById(@PathVariable Long id) {
                if (sucursalService.deleteById(id)) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sucursal eliminada");
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body("Sucursal no encontrada o no se pudo eliminar");
                }
        }
}
