package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Reserva;
import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.model.Reserva.EstadoReserva;
import com.viaRapida.viaRapidaProyect.service.ReservaService;
import com.viaRapida.viaRapidaProyect.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.findAll();
        return ResponseEntity.ok(reservas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> getReservasByCliente(@PathVariable Long clienteId) {
        List<Reserva> reservas = reservaService.findByClienteId(clienteId);
        return ResponseEntity.ok(reservas);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> getReservasByEstado(@PathVariable EstadoReserva estado) {
        List<Reserva> reservas = reservaService.findByEstado(estado);
        return ResponseEntity.ok(reservas);
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<Reserva>> getReservasByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Reserva> reservas = reservaService.findByFechaReservaBetween(fechaInicio, fechaFin);
        return ResponseEntity.ok(reservas);
    }
    
    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.save(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Reserva> confirmarReserva(@PathVariable Long id) {
        try {
            Reserva reservaConfirmada = reservaService.confirmarReserva(id);
            return ResponseEntity.ok(reservaConfirmada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        try {
            Reserva reservaCancelada = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(reservaCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/completar")
    public ResponseEntity<Reserva> completarReserva(@PathVariable Long id) {
        try {
            Reserva reservaCompletada = reservaService.completarReserva(id);
            return ResponseEntity.ok(reservaCompletada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        try {
            reservaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}