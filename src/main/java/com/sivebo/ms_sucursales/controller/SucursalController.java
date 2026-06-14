package com.sivebo.ms_sucursales.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.dto.request.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Sucursal;
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
@Tag(name="Sucursales", description = "Operaciones relacionadas con las sucursales")
public class SucursalController {

        private final SucursalService sucursalService;
        
        @GetMapping
        @Operation(summary = "Obtener todas las sucursales registradas", description = "Obtiene una lista de todas las sucursales")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Sucursales obtenidas exitosamente",
                        content = @Content(mediaType = "aplication/json",
                        schema = @Schema(implementation = Sucursal.class)
                        )
                )
        }
        )
        public List<SucursalResponseDTO> getAll() {
                return sucursalService.getAll();
        }

        @GetMapping("{id}")
        public ResponseEntity<SucursalResponseDTO> getById(@PathVariable Long id) {
                return sucursalService.getById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/search")
        public ResponseEntity<?> getByAtribute(
                @RequestParam(required = false) String nombre,
                @RequestParam(required = false) String idComuna){

                List<String> params = new ArrayList<>(List.of(nombre, idComuna));

                int num_null = 0;
                for(String value: params){
                        if(value == null) num_null++;
                }
                if(num_null == params.size()) {
                        return ResponseEntity.badRequest().body("Debe proporcionar un atributo de búsqueda valido");
                }else if(num_null > 1) {
                        return ResponseEntity.badRequest().body("Solo se permite un atributo de búsqueda a la vez");
                }else if(nombre != null) {
                        log.info(">>> Buscando sucursal por nombre: {}", nombre);
                        return sucursalService.getByNombre(nombre)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
                }else if(idComuna != null){
                        log.info(">>> Buscando sucursal por idComuna: {}", idComuna);
                        return ResponseEntity.ok(sucursalService.getByComunaId(Long.valueOf(idComuna)));
                }else{
                        return ResponseEntity.internalServerError().body("Error en el URL query");
                }
        }

        @PostMapping 
        public ResponseEntity<SucursalResponseDTO> create(@Valid @RequestBody SucursalRequestDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(sucursalService.create(dto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<SucursalResponseDTO> update(
                @PathVariable Long id,
                @Valid @RequestBody SucursalRequestDTO dto) {
                
                        return sucursalService.update(id, dto)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteById(@PathVariable Long id) {
                if (sucursalService.deleteById(id)) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sucursal eliminada");
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada o no se pudo eliminar");
                }
        }
}
