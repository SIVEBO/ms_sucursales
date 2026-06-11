package com.sivebo.ms_sucursales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

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
