package com.viaRapida.viaRapidaProyect.serviceImpl;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.repository.TicketRepository;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class TicketServiceImpl implements TicketService {
 
    @Autowired
    private TicketRepository ticketRepository;
 
    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
 
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
 
    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }
 
    @Override
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }
 
    @Override
    public List<Ticket> getTicketsByDni(String dni) {
        return ticketRepository.findByDni(dni);
    }
 
    @Override
    public double calculatePrice(String fromLocation, String toLocation) {
        if ((fromLocation.equals("Huamanga") && toLocation.equals("San Miguel")) ||
            (fromLocation.equals("San Miguel") && toLocation.equals("Huamanga"))) {
            return 25.0;
        } else if ((fromLocation.equals("Huamanga") && toLocation.equals("Huanta")) ||
                   (fromLocation.equals("Huanta") && toLocation.equals("Huamanga"))) {
            return 10.0;
        } else if ((fromLocation.equals("Huanta") && toLocation.equals("San Miguel")) ||
                   (fromLocation.equals("San Miguel") && toLocation.equals("Huanta"))) {
            return 15.0;
        }
        return 0.0;
    }
}