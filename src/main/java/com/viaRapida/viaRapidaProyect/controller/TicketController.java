package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                            @RequestParam("dni") String dni,
                            @RequestParam("fromLocation") String fromLocation,
                            @RequestParam("toLocation") String toLocation,
                            @RequestParam("travelDate") String travelDate,
                            @RequestParam("travelTime") String travelTime,
                            @RequestParam("seatNumber") String seatNumber,
                            Model model) {
        try {
            LocalDate parsedDate = LocalDate.parse(travelDate);
            Ticket ticket = new Ticket();
            ticket.setPassengerName(passengerName);
            ticket.setDni(dni);
            ticket.setFromLocation(fromLocation);
            ticket.setToLocation(toLocation);
            ticket.setTravelDate(parsedDate);
            ticket.setTravelTime(travelTime);
            ticket.setSeatNumber(seatNumber);

            // Calcular el precio basado en los destinos seleccionados
            double price = calculatePrice(fromLocation, toLocation);
            ticket.setPrice(price);

            ticketService.saveTicket(ticket);
            model.addAttribute("ticket", ticket);
            return "confirmation";
        } catch (DateTimeParseException e) {
            // Manejar el error de formato de fecha
            return "redirect:/?error=invalidDate";
        }
    }

    private double calculatePrice(String fromLocation, String toLocation) {
        if ((fromLocation.equals("Huamanga") && toLocation.equals("San Miguel")) ||
            (fromLocation.equals("San Miguel") && toLocation.equals("Huamanga"))) {
            return 25.0;
        } else if ((fromLocation.equals("Huamanga") && toLocation.equals("Huanta")) ||
                   (fromLocation.equals("Huanta") && toLocation.equals("Huamanga"))) {
            return 10.0;
        }
        return 0.0;
    }
}