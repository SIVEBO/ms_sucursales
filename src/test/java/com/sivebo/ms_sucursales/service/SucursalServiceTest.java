package com.sivebo.ms_sucursales.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sivebo.ms_sucursales.dto.request.SucursalRequestDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.exception.EntityNotFoundException;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.EstadoSucursal;
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

        responseDTO = new SucursalResponseDTO(1L, "Test Sucursal", "Test Comuna", "Test Region", "Dir", "123", EstadoSucursal.ACTIVA);
    }

    @Test
    void getByIdShouldReturnSucursal() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        Optional<SucursalResponseDTO> result = sucursalService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Sucursal", result.get().getNombre());
        verify(sucursalRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdNoEncontradoRetornaVacio() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<SucursalResponseDTO> result = sucursalService.getById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllRetornaListaMapeada() {
        when(sucursalRepository.findByEstado(EstadoSucursal.ACTIVA)).thenReturn(List.of(sucursal));
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        List<SucursalResponseDTO> result = sucursalService.getAll();

        assertEquals(1, result.size());
        assertEquals("Test Sucursal", result.get(0).getNombre());
    }

    @Test
    void createComunaExistenteGuardaYRetornaDTO() {
        SucursalRequestDTO dto = new SucursalRequestDTO("Test Sucursal", "Santiago", "Calle 1", "123");
        Comuna comuna = new Comuna();

        when(comunaRepository.findByNombre("Santiago")).thenReturn(Optional.of(comuna));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        SucursalResponseDTO result = sucursalService.create(dto);

        assertNotNull(result);
        assertEquals("Test Sucursal", result.getNombre());
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    void createComunaNoExistenteLanzaEntityNotFoundException() {
        SucursalRequestDTO dto = new SucursalRequestDTO("Test", "Inexistente", "Calle", null);

        when(comunaRepository.findByNombre("Inexistente")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sucursalService.create(dto));
        verify(sucursalRepository, never()).save(any());
    }

    @Test
    void deactivateEncontradoCambiaEstadoAInactiva() {
        Sucursal inactiva = new Sucursal(1L, "Test Sucursal", null, "Dir", "123", EstadoSucursal.INACTIVA);
        SucursalResponseDTO inactivaDTO = new SucursalResponseDTO(1L, "Test Sucursal", "Test Comuna", "Test Region", "Dir", "123", EstadoSucursal.INACTIVA);

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(inactiva);
        when(mapperUtil.mapSucursalToDTO(inactiva)).thenReturn(inactivaDTO);

        Optional<SucursalResponseDTO> result = sucursalService.deactivate(1L);

        assertTrue(result.isPresent());
        assertEquals(EstadoSucursal.INACTIVA, result.get().getEstado());
    }
}
