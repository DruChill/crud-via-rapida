package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.Ticket;
import com.viaRapida.viaRapidaProyect.repository.TicketRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }
}