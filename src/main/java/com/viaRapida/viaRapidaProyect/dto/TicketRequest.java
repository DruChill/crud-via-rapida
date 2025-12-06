package com.viaRapida.viaRapidaProyect.dto;

import java.time.LocalDate;

public class TicketRequest {
    // Datos del usuario
    private String dni;
    private String name;
    private String email;
    private String phone;
    
    // IDs de destinos
    private Long fromDestinationId;
    private Long toDestinationId;
    
    // Datos del viaje
    private LocalDate travelDate;
    private String travelTime;
    private String seatNumber;
    private double price;

    // Constructors
    public TicketRequest() {
    }

    // Getters and setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getFromDestinationId() {
        return fromDestinationId;
    }

    public void setFromDestinationId(Long fromDestinationId) {
        this.fromDestinationId = fromDestinationId;
    }

    public Long getToDestinationId() {
        return toDestinationId;
    }

    public void setToDestinationId(Long toDestinationId) {
        this.toDestinationId = toDestinationId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
