package com.viaRapida.viaRapidaProyect.serviceImpl;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.model.Ruta;
import com.viaRapida.viaRapidaProyect.repository.TicketRepository;
import com.viaRapida.viaRapidaProyect.repository.RutaRepository;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
 
@Service
public class TicketServiceImpl implements TicketService {
 
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private RutaRepository rutaRepository;
 
    @Override
    public void saveTicket(Ticket ticket) {
        // Calcular precio automáticamente si no está establecido
        if (ticket.getPrice() == null) {
            BigDecimal price = calculatePriceBD(ticket.getFromLocation(), ticket.getToLocation());
            ticket.setPrice(price);
        }
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
        return calculatePriceBD(fromLocation, toLocation).doubleValue();
    }
    
    public BigDecimal calculatePriceBD(String fromLocation, String toLocation) {
        // Buscar la ruta en la base de datos
        Optional<Ruta> rutaOpt = rutaRepository.findByOrigenAndDestino(fromLocation, toLocation);
        if (rutaOpt.isPresent()) {
            Ruta ruta = rutaOpt.get();
            // Calcular precio basado en distancia (ejemplo: 0.10 por km)
            return BigDecimal.valueOf(ruta.getDistanciaKm() * 0.10);
        }
        
        // También buscar ruta inversa
        rutaOpt = rutaRepository.findByOrigenAndDestino(toLocation, fromLocation);
        if (rutaOpt.isPresent()) {
            Ruta ruta = rutaOpt.get();
            return BigDecimal.valueOf(ruta.getDistanciaKm() * 0.10);
        }
        
        // Fallback a precios fijos originales
        if ((fromLocation.equals("Huamanga") && toLocation.equals("San Miguel")) ||
            (fromLocation.equals("San Miguel") && toLocation.equals("Huamanga"))) {
            return BigDecimal.valueOf(25.0);
        } else if ((fromLocation.equals("Huamanga") && toLocation.equals("Huanta")) ||
                   (fromLocation.equals("Huanta") && toLocation.equals("Huamanga"))) {
            return BigDecimal.valueOf(10.0);
        } else if ((fromLocation.equals("Huanta") && toLocation.equals("San Miguel")) ||
                   (fromLocation.equals("San Miguel") && toLocation.equals("Huanta"))) {
            return BigDecimal.valueOf(15.0);
        }
        return BigDecimal.valueOf(0.0);
    }
}