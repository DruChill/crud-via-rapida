package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam("passengerName") String passengerName,
                            @RequestParam("numberOfPassengers") int numberOfPassengers,
                            @RequestParam("destinationId") Long destinationId,
                            @RequestParam("fromLocation") String fromLocation,
                            @RequestParam("toLocation") String toLocation,
                            @RequestParam("travelDate") String travelDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(travelDate);
            Ticket ticket = new Ticket();
            ticket.setPassengerName(passengerName);
            ticket.setNumberOfPassengers(numberOfPassengers);
            ticket.setDestinationId(destinationId);
            ticket.setFromLocation(fromLocation);
            ticket.setToLocation(toLocation);
            ticket.setTravelDate(parsedDate);
            ticketService.saveTicket(ticket);
            return "redirect:/";
        } catch (DateTimeParseException e) {
            // Manejar el error de formato de fecha
            return "redirect:/?error=invalidDate";
        }
    }
}