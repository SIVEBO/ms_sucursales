package com.sivebo.ms_sucursales.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;
import com.sivebo.ms_sucursales.utils.MapperUtil;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursal;
    private SucursalResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Test Sucursal");

        responseDTO = new SucursalResponseDTO(1L, "Test Sucursal", "Test Comuna", "Test Region", "Dir", "123");
    }

    @Test
    void getById_ShouldReturnSucursal() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        Optional<SucursalResponseDTO> result = sucursalService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Sucursal", result.get().getNombre());
        verify(sucursalRepository, times(1)).findById(1L);
    }
}
