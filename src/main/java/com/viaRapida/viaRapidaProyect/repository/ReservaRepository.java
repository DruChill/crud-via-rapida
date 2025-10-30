package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Reserva;
import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.model.Reserva.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    List<Reserva> findByCliente(Cliente cliente);
    
    List<Reserva> findByEstado(EstadoReserva estado);
    
    List<Reserva> findByClienteAndEstado(Cliente cliente, EstadoReserva estado);
    
    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findByFechaReservaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                           @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :clienteId ORDER BY r.fechaReserva DESC")
    List<Reserva> findByClienteIdOrderByFechaReservaDesc(@Param("clienteId") Long clienteId);
    
    @Query("SELECT r FROM Reserva r WHERE r.estado = :estado ORDER BY r.fechaReserva ASC")
    List<Reserva> findByEstadoOrderByFechaReservaAsc(@Param("estado") EstadoReserva estado);
    
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.vehiculo.id = :vehiculoId AND r.estado IN :estados")
    Long countByVehiculoIdAndEstadoIn(@Param("vehiculoId") Long vehiculoId, @Param("estados") List<EstadoReserva> estados);
}