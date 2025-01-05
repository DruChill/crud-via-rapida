package com.viaRapida.viaRapidaProyect.controller;

import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.DestinationService;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Destination> listDestinations = destinationService.getAllDestinations();
        model.addAttribute("listDestinations", listDestinations);

        List<String> purchasedSeats = ticketService.getAllTickets().stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());
        model.addAttribute("purchasedSeats", purchasedSeats);

        List<String> seatList = Arrays.asList("1-A", "2-A", "3-A", "4-A");
        model.addAttribute("seatList", seatList);

        return "index";
    }
}