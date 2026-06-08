package com.sivebo.ms_sucursales.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/regiones")
@RequiredArgsConstructor
public class RegionController {
    private final RegionRepository repository;

    @GetMapping
    public List<Region> getAll() {
        return repository.findAll();
    }
}
