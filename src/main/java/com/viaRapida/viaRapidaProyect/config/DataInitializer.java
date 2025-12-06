package com.viaRapida.viaRapidaProyect.config;

import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.model.User;
import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.repository.DestinationRepository;
import com.viaRapida.viaRapidaProyect.repository.UserRepository;
import com.viaRapida.viaRapidaProyect.repository.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(
            DestinationRepository destinationRepository,
            UserRepository userRepository,
            TicketRepository ticketRepository) {
        return args -> {
            // Inicializar destinos
            if (destinationRepository.count() == 0) {
                Destination huamanga = destinationRepository.save(
                    new Destination(null, "Huamanga", "Capital de Ayacucho", 25.0));
                Destination huanta = destinationRepository.save(
                    new Destination(null, "Huanta", "Ciudad al norte de Ayacucho", 15.0));
                Destination sanMiguel = destinationRepository.save(
                    new Destination(null, "San Miguel", "Distrito de Ayacucho", 10.0));
                
                // Crear usuarios de ejemplo (opcional, puedes comentar esta sección)
                if (userRepository.count() == 0) {
                    User user1 = userRepository.save(
                        new User("12345678", "Juan Pérez", "juan@example.com", "987654321"));
                    User user2 = userRepository.save(
                        new User("87654321", "María García", "maria@example.com", "912345678"));
                    
                    // Crear tickets de ejemplo (opcional)
                    ticketRepository.save(createTicket(user1, huamanga, huanta, LocalDate.now().plusDays(1), "08:00", "A1", 15.0));
                    ticketRepository.save(createTicket(user2, huanta, sanMiguel, LocalDate.now().plusDays(2), "10:00", "B2", 10.0));
                }
            }
        };
    }
    
    private Ticket createTicket(User user, Destination from, Destination to, 
                                LocalDate date, String time, String seat, double price) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFromDestination(from);
        ticket.setToDestination(to);
        ticket.setTravelDate(date);
        ticket.setTravelTime(time);
        ticket.setSeatNumber(seat);
        ticket.setPrice(price);
        return ticket;
    }
}