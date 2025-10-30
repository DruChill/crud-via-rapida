package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.Reserva;
import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.model.Reserva.EstadoReserva;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaService {
    
    Reserva save(Reserva reserva);
    
    Optional<Reserva> findById(Long id);
    
    List<Reserva> findAll();
    
    List<Reserva> findByCliente(Cliente cliente);
    
    List<Reserva> findByEstado(EstadoReserva estado);
    
    List<Reserva> findByClienteAndEstado(Cliente cliente, EstadoReserva estado);
    
    List<Reserva> findByFechaReservaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Reserva> findByClienteId(Long clienteId);
    
    Reserva confirmarReserva(Long id);
    
    Reserva cancelarReserva(Long id);
    
    Reserva completarReserva(Long id);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    Long contarReservasPorVehiculo(Long vehiculoId, List<EstadoReserva> estados);
}