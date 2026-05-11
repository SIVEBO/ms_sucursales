package com.sivebo.ms_sucursales.controller;

import java.util.List;
import java.util.Map;

import com.sivebo.ms_sucursales.service.SucursalService;
import com.sivebo.ms_sucursales.dto.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.SucursalResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


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
        public ResponseEntity<?> getByAtribute(@RequestParam Map<String, String> allParams){
                String atribute = allParams.getOrDefault("nombre", allParams.get("idComuna"));
            return switch (atribute) {
                case "nombre" -> sucursalService.getByNombre(allParams.get("nombre"))
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
                case "idComuna" -> ResponseEntity.ok(sucursalService.getByComunaId(Long.valueOf(allParams.get("idComuna"))));
                default -> ResponseEntity.badRequest().body("Atributo de búsqueda no válido");
            };
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
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada");
                }
        }
}
