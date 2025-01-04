package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            ticket.setNumberOfPassengers(numberOfPassengers);
            ticket.setDni(dni);
            ticket.setFromLocation(fromLocation);
            ticket.setToLocation(toLocation);
            ticket.setTravelDate(parsedDate);
            ticket.setTravelTime(travelTime);
            ticket.setSeatNumber(seatNumber);
            ticketService.saveTicket(ticket);
            model.addAttribute("ticket", ticket);
            return "confirmation";
        } catch (DateTimeParseException e) {
            // Manejar el error de formato de fecha
            return "redirect:/?error=invalidDate";
        }
    }

    @GetMapping("/finalizado")
    public String showConfirmation(Model model) {
        // Aquí puedes agregar lógica para obtener el ticket si es necesario
        return "confirmation";
    }
}