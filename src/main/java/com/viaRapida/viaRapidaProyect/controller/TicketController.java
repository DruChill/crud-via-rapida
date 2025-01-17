package com.viaRapida.viaRapidaProyect.controller;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.service.TicketService;
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
    public String buyTicket(@ModelAttribute Ticket ticket, Model model) {
        ticketService.saveTicket(ticket);
        model.addAttribute("ticket", ticket);
        return "confirmation";
    }
}