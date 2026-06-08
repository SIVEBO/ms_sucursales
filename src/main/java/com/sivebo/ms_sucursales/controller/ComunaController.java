package com.sivebo.ms_sucursales.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/comunas")
@RequiredArgsConstructor
public class ComunaController {
    private final ComunaRepository repository;

    @GetMapping
    public List<Comuna> getAll() {
        return repository.findAll();
    }
}
