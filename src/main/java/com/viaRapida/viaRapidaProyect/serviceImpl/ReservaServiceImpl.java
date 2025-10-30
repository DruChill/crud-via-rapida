package com.viaRapida.viaRapidaProyect.serviceImpl;

import com.viaRapida.viaRapidaProyect.model.Reserva;
import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.model.Reserva.EstadoReserva;
import com.viaRapida.viaRapidaProyect.repository.ReservaRepository;
import com.viaRapida.viaRapidaProyect.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    
    @Override
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }
    
    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }
    
    @Override
    public List<Reserva> findByCliente(Cliente cliente) {
        return reservaRepository.findByCliente(cliente);
    }
    
    @Override
    public List<Reserva> findByEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }
    
    @Override
    public List<Reserva> findByClienteAndEstado(Cliente cliente, EstadoReserva estado) {
        return reservaRepository.findByClienteAndEstado(cliente, estado);
    }
    
    @Override
    public List<Reserva> findByFechaReservaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservaRepository.findByFechaReservaBetween(fechaInicio, fechaFin);
    }
    
    @Override
    public List<Reserva> findByClienteId(Long clienteId) {
        return reservaRepository.findByClienteIdOrderByFechaReservaDesc(clienteId);
    }
    
    @Override
    public Reserva confirmarReserva(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            return reservaRepository.save(reserva);
        }
        throw new RuntimeException("Reserva no encontrada con ID: " + id);
    }
    
    @Override
    public Reserva cancelarReserva(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstado(EstadoReserva.CANCELADA);
            return reservaRepository.save(reserva);
        }
        throw new RuntimeException("Reserva no encontrada con ID: " + id);
    }
    
    @Override
    public Reserva completarReserva(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstado(EstadoReserva.COMPLETADA);
            return reservaRepository.save(reserva);
        }
        throw new RuntimeException("Reserva no encontrada con ID: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
    }
    
    @Override
    public boolean existsById(Long id) {
        return reservaRepository.existsById(id);
    }
    
    @Override
    public Long contarReservasPorVehiculo(Long vehiculoId, List<EstadoReserva> estados) {
        return reservaRepository.countByVehiculoIdAndEstadoIn(vehiculoId, estados);
    }
}