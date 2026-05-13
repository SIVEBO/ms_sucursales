package com.sivebo.ms_sucursales.controller;

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

import com.sivebo.ms_sucursales.dto.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.SucursalResponseDTO;
import com.sivebo.ms_sucursales.service.SucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/sucursales")
@RequiredArgsConstructor
public class SucursalController {

        private final SucursalService sucursalService;
        
        @GetMapping
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
                @RequestParam(value = "nombre", required = false) String nombre,
                @RequestParam(value = "idComuna", required = false) String idComuna){

                if(nombre == null && idComuna == null) {
                        return ResponseEntity.badRequest().body("Debe proporcionar un atributo de búsqueda valido");
                }else if(nombre != null && idComuna != null) {
                        return ResponseEntity.badRequest().body("Solo se permite un atributo de búsqueda a la vez");
                }else if(nombre != null) {
                        log.info(">>> Buscando sucursal por nombre: {}", nombre);
                        return sucursalService.getByNombre(nombre)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
                }else {
                        log.info(">>> Buscando sucursal por idComuna: {}", idComuna);
                        return ResponseEntity.ok(sucursalService.getByComunaId(Long.valueOf(idComuna)));
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
        public ResponseEntity<String> delete(@PathVariable Long id) {
                if (sucursalService.delete(id)) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sucursal eliminada");
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada o no se pudo eliminar");
                }
        }
}
