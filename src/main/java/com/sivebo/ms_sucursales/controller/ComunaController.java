package com.sivebo.ms_sucursales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.repository.ComunaRepository;

import lombok.RequiredArgsConstructor;

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
