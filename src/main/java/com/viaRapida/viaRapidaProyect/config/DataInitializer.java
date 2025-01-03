package com.viaRapida.viaRapidaProyect.config;

import com.viaRapida.viaRapidaProyect.model.Destination;
import com.viaRapida.viaRapidaProyect.repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(DestinationRepository destinationRepository) {
        return args -> {
            if (destinationRepository.count() == 0) {
                destinationRepository.save(new Destination(null, "Huamanga", "Descripción de Huamanga"));
                destinationRepository.save(new Destination(null, "Huanta", "Descripción de Huanta"));
                destinationRepository.save(new Destination(null, "San Miguel", "Descripción de San Miguel"));
            }
        };
    }
}