package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Pasaje;
import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.model.Ruta;
import com.viaRapida.viaRapidaProyect.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PasajeRepository extends JpaRepository<Pasaje, Long> {
    
    List<Pasaje> findByCliente(Cliente cliente);
    
    List<Pasaje> findByRuta(Ruta ruta);
    
    List<Pasaje> findByVehiculo(Vehiculo vehiculo);
    
    @Query("SELECT p FROM Pasaje p WHERE p.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Pasaje> findByFechaReservaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                          @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pasaje p WHERE p.cliente.id = :clienteId ORDER BY p.fechaReserva DESC")
    List<Pasaje> findByClienteIdOrderByFechaReservaDesc(@Param("clienteId") Long clienteId);
    
    @Query("SELECT p FROM Pasaje p WHERE p.ruta.origen = :origen AND p.ruta.destino = :destino")
    List<Pasaje> findByOrigenAndDestino(@Param("origen") String origen, @Param("destino") String destino);
}