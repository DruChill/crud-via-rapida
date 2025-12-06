package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Buscar tickets por usuario
    List<Ticket> findByUser(User user);
    List<Ticket> findByUserId(Long userId);
    
    // Buscar tickets por destino
    List<Ticket> findByFromDestination(Destination fromDestination);
    List<Ticket> findByToDestination(Destination toDestination);
    List<Ticket> findByFromDestinationAndToDestination(Destination fromDestination, Destination toDestination);
    
    // Buscar tickets por fecha
    List<Ticket> findByTravelDate(LocalDate travelDate);
    List<Ticket> findByTravelDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Combinaciones útiles
    List<Ticket> findByUserAndTravelDate(User user, LocalDate travelDate);
    List<Ticket> findByFromDestinationAndToDestinationAndTravelDate(
        Destination fromDestination, Destination toDestination, LocalDate travelDate);
}