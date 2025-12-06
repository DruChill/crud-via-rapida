package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.DestinationService;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {
    
    @Autowired
    private DestinationService destinationService;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available-seats")
    public ResponseEntity<List<String>> getAvailableSeats(
            @RequestParam String fromLocation,
            @RequestParam String toLocation,
            @RequestParam String travelDate,
            @RequestParam String travelTime) {
        
        List<String> purchasedSeats = ticketService.getAllTickets().stream()
                .filter(ticket -> ticket.getFromDestination() != null && 
                        ticket.getFromDestination().getName().equals(fromLocation))
                .filter(ticket -> ticket.getToDestination() != null && 
                        ticket.getToDestination().getName().equals(toLocation))
                .filter(ticket -> ticket.getTravelDate().equals(LocalDate.parse(travelDate)))
                .filter(ticket -> ticket.getTravelTime().equals(travelTime))
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        List<String> allSeats = Arrays.asList("1-A", "2-A", "3-A", "4-A");
        List<String> availableSeats = allSeats.stream()
                .filter(seat -> !purchasedSeats.contains(seat))
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/purchased-seats")
    public ResponseEntity<List<String>> getPurchasedSeats(
            @RequestParam String fromLocation,
            @RequestParam String toLocation,
            @RequestParam String travelDate,
            @RequestParam String travelTime) {
        
        List<String> purchasedSeats = ticketService.getAllTickets().stream()
                .filter(ticket -> ticket.getFromDestination() != null && 
                        ticket.getFromDestination().getName().equals(fromLocation))
                .filter(ticket -> ticket.getToDestination() != null && 
                        ticket.getToDestination().getName().equals(toLocation))
                .filter(ticket -> ticket.getTravelDate().equals(LocalDate.parse(travelDate)))
                .filter(ticket -> ticket.getTravelTime().equals(travelTime))
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok(purchasedSeats);
    }

    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        Destination savedDestination = destinationService.saveDestination(destination);
        return ResponseEntity.ok(savedDestination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destinationDetails) {
        return destinationService.getDestinationById(id)
                .map(destination -> {
                    destination.setName(destinationDetails.getName());
                    destination.setDescription(destinationDetails.getDescription());
                    return ResponseEntity.ok(destinationService.saveDestination(destination));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        if (destinationService.getDestinationById(id).isPresent()) {
            destinationService.deleteDestination(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}