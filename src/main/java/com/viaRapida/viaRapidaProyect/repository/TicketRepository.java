package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByDni(String dni);
}