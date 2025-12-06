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
                    new Destination(null, "Huamanga", "Capital de Ayacucho - Plaza de Armas y Templos Coloniales", 25.0));
                Destination huanta = destinationRepository.save(
                    new Destination(null, "Huanta", "Ciudad al norte de Ayacucho - Clima cálido", 15.0));
                Destination sanMiguel = destinationRepository.save(
                    new Destination(null, "San Miguel", "Distrito turístico de Ayacucho", 10.0));
                Destination quinua = destinationRepository.save(
                    new Destination(null, "Quinua", "Pampa de Ayacucho - Batalla Histórica", 20.0));
                Destination vilcashuaman = destinationRepository.save(
                    new Destination(null, "Vilcashuamán", "Capital del Chinchaysuyo - Ruinas Incas", 30.0));
                
                System.out.println("✅ Se crearon " + destinationRepository.count() + " destinos");
                
                // Crear usuarios de ejemplo
                if (userRepository.count() == 0) {
                    User user1 = userRepository.save(
                        new User("12345678", "Juan Pérez García", "juan.perez@example.com", "987654321"));
                    User user2 = userRepository.save(
                        new User("87654321", "María García López", "maria.garcia@example.com", "912345678"));
                    User user3 = userRepository.save(
                        new User("11223344", "Carlos Rodríguez", "carlos.r@example.com", "923456789"));
                    User user4 = userRepository.save(
                        new User("55667788", "Ana Martínez", "ana.m@example.com", "934567890"));
                    User user5 = userRepository.save(
                        new User("99887766", "Luis Sánchez", "luis.s@example.com", "945678901"));
                    
                    System.out.println("✅ Se crearon " + userRepository.count() + " usuarios");
                    
                    // Crear tickets de ejemplo
                    ticketRepository.save(createTicket(user1, huamanga, huanta, 
                        LocalDate.now().plusDays(1), "08:00", "1-A", 15.0));
                    ticketRepository.save(createTicket(user2, huanta, sanMiguel, 
                        LocalDate.now().plusDays(2), "10:00", "2-A", 10.0));
                    ticketRepository.save(createTicket(user1, huamanga, quinua, 
                        LocalDate.now().plusDays(3), "12:00", "1-B", 20.0));
                    ticketRepository.save(createTicket(user3, sanMiguel, huamanga, 
                        LocalDate.now().plusDays(1), "16:00", "3-A", 10.0));
                    ticketRepository.save(createTicket(user4, huamanga, vilcashuaman, 
                        LocalDate.now().plusDays(4), "05:00", "2-B", 30.0));
                    ticketRepository.save(createTicket(user5, vilcashuaman, huamanga, 
                        LocalDate.now().plusDays(5), "14:00", "4-A", 30.0));
                    ticketRepository.save(createTicket(user2, quinua, huamanga, 
                        LocalDate.now().plusDays(2), "09:00", "1-C", 20.0));
                    
                    System.out.println("✅ Se crearon " + ticketRepository.count() + " tickets de ejemplo");
                    System.out.println("🚀 Sistema listo con datos de prueba!");
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