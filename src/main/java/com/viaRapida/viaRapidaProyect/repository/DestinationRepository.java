package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Destination findByName(String name);
}