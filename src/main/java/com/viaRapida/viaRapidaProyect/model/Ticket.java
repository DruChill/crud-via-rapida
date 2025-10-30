package com.viaRapida.viaRapidaProyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "passenger_name", nullable = false, length = 100)
    private String passengerName;
    
    @Column(nullable = false, length = 20)
    private String dni;
    
    @Column(name = "from_location", nullable = false, length = 100)
    private String fromLocation;
    
    @Column(name = "to_location", nullable = false, length = 100)
    private String toLocation;
    
    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;
    
    @Column(name = "travel_time", length = 10)
    private String travelTime;
    
    @Column(name = "seat_number", length = 10)
    private String seatNumber;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Relaciones opcionales para compatibilidad con el nuevo sistema
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Ticket() {
        this.createdAt = LocalDateTime.now();
    }

    public Ticket(String passengerName, String dni, String fromLocation, String toLocation, 
                  LocalDate travelDate, String travelTime, String seatNumber, BigDecimal price) {
        this.passengerName = passengerName;
        this.dni = dni;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
        this.seatNumber = seatNumber;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    // Métodos de compatibilidad
    public double getPriceAsDouble() {
        return price != null ? price.doubleValue() : 0.0;
    }
    
    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }
}