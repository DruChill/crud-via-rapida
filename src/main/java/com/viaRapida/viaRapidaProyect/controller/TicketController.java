package com.viaRapida.viaRapidaProyect.controller;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.dto.TicketRequest;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import com.viaRapida.viaRapidaProyect.serviceImpl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
 
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }
 
    @GetMapping("/search")
    public ResponseEntity<List<Ticket>> getTicketsByDni(@RequestParam String dni) {
        List<Ticket> tickets = ticketService.getTicketsByDni(dni);
        return ResponseEntity.ok(tickets);
    }
    
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketServiceImpl.createTicketFromRequest(ticketRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear el ticket: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {
        try {
            Ticket existingTicket = ticketService.getTicketById(id);
            if (existingTicket == null) {
                return ResponseEntity.notFound().build();
            }
            Ticket updatedTicket = ticketServiceImpl.updateTicketFromRequest(id, ticketRequest);
            return ResponseEntity.ok(updatedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al actualizar el ticket: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            ticketService.deleteTicketById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Clase interna para respuestas de error
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}