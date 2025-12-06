package com.viaRapida.viaRapidaProyect.controller;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.dto.TicketRequest;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import com.viaRapida.viaRapidaProyect.serviceImpl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
 
@Controller
public class TicketController {
 
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private TicketServiceImpl ticketServiceImpl;
 
    @GetMapping("/buscarMiTicket")
    public String buscarMiTicket(@RequestParam(value = "dni", required = false) String dni, Model model) {
        List<Ticket> tickets = List.of();
        if (dni != null && !dni.isEmpty()) {
            tickets = ticketService.getTicketsByDni(dni);
        }
        model.addAttribute("tickets", tickets);
        return "buscarMiTicket";
    }
 
    @GetMapping("/comentarios")
    public String Comentarios() {
        return "Comentario";
    }
    
    @PostMapping("/buyTicket")
    public String buyTicket(@ModelAttribute TicketRequest ticketRequest, Model model) {
        try {
            Ticket ticket = ticketServiceImpl.createTicketFromRequest(ticketRequest);
            model.addAttribute("ticket", ticket);
            return "confirmation";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el ticket: " + e.getMessage());
            return "index";
        }
    }
}