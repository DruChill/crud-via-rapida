package com.viaRapida.viaRapidaProyect.config;

import com.viaRapida.viaRapidaProyect.model.*;
import com.viaRapida.viaRapidaProyect.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(
            DestinoRepository destinoRepository,
            ClienteRepository clienteRepository,
            VehiculoRepository vehiculoRepository,
            RutaRepository rutaRepository,
            PasajeRepository pasajeRepository,
            ReservaRepository reservaRepository) {
        
        return args -> {
            // Cargar destinos (compatibilidad)
            if (destinoRepository.count() == 0) {
                destinoRepository.save(new Destino(null, "Huamanga", "Capital de Ayacucho", BigDecimal.valueOf(25.00)));
                destinoRepository.save(new Destino(null, "Huanta", "Ciudad histórica", BigDecimal.valueOf(15.00)));
                destinoRepository.save(new Destino(null, "San Miguel", "Pueblo tradicional", BigDecimal.valueOf(30.00)));
            }
            
            // Cargar clientes
            if (clienteRepository.count() == 0) {
                clienteRepository.save(new Cliente("Juan", "Pérez", "juan.perez@email.com", "987654321"));
                clienteRepository.save(new Cliente("María", "García", "maria.garcia@email.com", "987654322"));
                clienteRepository.save(new Cliente("Carlos", "López", "carlos.lopez@email.com", "987654323"));
            }
            
            // Cargar vehículos
            if (vehiculoRepository.count() == 0) {
                vehiculoRepository.save(new Vehiculo("Bus", "Mercedes", "Sprinter", 20));
                vehiculoRepository.save(new Vehiculo("Minivan", "Toyota", "Hiace", 12));
                vehiculoRepository.save(new Vehiculo("Bus", "Volvo", "B7R", 45));
            }
            
            // Cargar rutas
            if (rutaRepository.count() == 0) {
                rutaRepository.save(new Ruta("Lima", "Huamanga", 550));
                rutaRepository.save(new Ruta("Huamanga", "Huanta", 45));
                rutaRepository.save(new Ruta("Huamanga", "San Miguel", 125));
                rutaRepository.save(new Ruta("Lima", "Huanta", 595));
            }
        };
    }
}