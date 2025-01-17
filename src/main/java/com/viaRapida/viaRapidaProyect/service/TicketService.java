package com.viaRapida.viaRapidaProyect.service;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
 
import java.util.List;
 
public interface TicketService {
    void saveTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket getTicketById(Long id);
    void deleteTicketById(Long id);
    List<Ticket> getTicketsByDni(String dni);
    double calculatePrice(String fromLocation, String toLocation);
}