package com.sivebo.ms_sucursales.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
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
            @ApiResponse(responseCode = "200", description = "Comunas obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comuna.class)))
    })
    @GetMapping
    public List<ComunaResponseDTO> getAll() {
        return comunaService.getAll();
    }

    @Operation(summary = "Obtener todas las comunas registradas por query", description = "Obtiene una lista o unidad de JSON de todas las comunas por query 'search?nombre=*' o 'search?nombreRegion=*'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunas obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comuna.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> getByAtribute(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String nombreRegion) {

        List<String> params = new ArrayList<>(Arrays.asList(nombre, nombreRegion));

        int num_null = 0;
        for (String value : params) {
            if (value == null)
                num_null++;
        }
        if (num_null != 1) {
            log.info(" Solo se permite un atributo de búsqueda a la vez pero ingresado {}", (params.size() - num_null));
            return ResponseEntity.badRequest().body(
                    "Solo se permite un atributo de búsqueda a la vez pero ingresado " + (params.size() - num_null));
        } else if (nombre != null) {
            log.info(">>> Buscando comuna por nombre: {}", nombre);
            return comunaService.getByNombre(nombre)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (nombreRegion != null) {
            log.info(">>> Buscando comuna por nombreRegion: {}", nombreRegion);
            return ResponseEntity.ok(comunaService.getByRegionNombre(nombreRegion));
        }
        return ResponseEntity.internalServerError().body("Error en el URL query");
    }

    @Operation(summary = "Obtener la comuna registradas por id", description = "Obtiene un JSON de la comuna registrada por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comuna.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<ComunaResponseDTO> getById(@PathVariable Long id) {
        return comunaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}