package com.viaRapida.viaRapidaProyect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pasaje_id", nullable = false)
    private Pasaje pasaje;
    
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;
    
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
    }

    public Reserva() {
        this.fechaReserva = LocalDateTime.now();
        this.estado = EstadoReserva.PENDIENTE;
    }

    public Reserva(Cliente cliente, Vehiculo vehiculo, Ruta ruta, Pasaje pasaje) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.ruta = ruta;
        this.pasaje = pasaje;
        this.fechaReserva = LocalDateTime.now();
        this.estado = EstadoReserva.PENDIENTE;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Pasaje getPasaje() {
        return pasaje;
    }

    public void setPasaje(Pasaje pasaje) {
        this.pasaje = pasaje;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}