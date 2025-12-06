package com.viaRapida.viaRapidaProyect.serviceImpl;
 
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.repository.TicketRepository;
import com.viaRapida.viaRapidaProyect.repository.UserRepository;
import com.viaRapida.viaRapidaProyect.repository.DestinationRepository;
import com.viaRapida.viaRapidaProyect.service.TicketService;
import com.viaRapida.viaRapidaProyect.dto.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class TicketServiceImpl implements TicketService {
 
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;
 
    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
    
    // Nuevo método para crear ticket desde DTO
    public Ticket createTicketFromRequest(TicketRequest request) {
        // Buscar o crear usuario
        User user = userRepository.findByDni(request.getDni())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setDni(request.getDni());
                    newUser.setName(request.getName());
                    newUser.setEmail(request.getEmail());
                    newUser.setPhone(request.getPhone());
                    return userRepository.save(newUser);
                });
        
        // Obtener destinos
        Destination fromDestination = destinationRepository.findById(request.getFromDestinationId())
                .orElseThrow(() -> new RuntimeException("Destino origen no encontrado"));
        Destination toDestination = destinationRepository.findById(request.getToDestinationId())
                .orElseThrow(() -> new RuntimeException("Destino destino no encontrado"));
        
        // Calcular precio automáticamente basado en los destinos
        double calculatedPrice = calculatePriceByDestinations(fromDestination, toDestination);
        
        // Crear ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFromDestination(fromDestination);
        ticket.setToDestination(toDestination);
        ticket.setTravelDate(request.getTravelDate());
        ticket.setTravelTime(request.getTravelTime());
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setPrice(calculatedPrice); // Usar precio calculado
        
        return ticketRepository.save(ticket);
    }
    
    // Método para calcular precio basado en destinos
    private double calculatePriceByDestinations(Destination from, Destination to) {
        // Lógica simple: suma de los precios base dividido entre 2 + 5 soles
        return ((from.getPrice() + to.getPrice()) / 2) + 5.0;
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
        User user = userRepository.findByDni(dni).orElse(null);
        if (user != null) {
            return ticketRepository.findByUser(user);
        }
        return List.of();
    }
 
    @Override
    public double calculatePrice(String fromLocation, String toLocation) {
        // Este método ahora es simplificado - el precio viene de la tabla Destination
        Destination from = destinationRepository.findAll().stream()
                .filter(d -> d.getName().equals(fromLocation))
                .findFirst()
                .orElse(null);
        Destination to = destinationRepository.findAll().stream()
                .filter(d -> d.getName().equals(toLocation))
                .findFirst()
                .orElse(null);
        
        if (from != null && to != null) {
            // Puedes implementar lógica más compleja aquí
            return Math.abs(from.getPrice() - to.getPrice()) + 10.0;
        }
        return 0.0;
    }
}