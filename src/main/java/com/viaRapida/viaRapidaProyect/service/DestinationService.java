package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.Destino;
import com.viaRapida.viaRapidaProyect.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {
    @Autowired
    private DestinoRepository destinoRepository;

    public List<Destino> getAllDestinations() {
        return destinoRepository.findAll();
    }

    public Destino getDestinationByName(String name) {
        List<Destino> destinos = destinoRepository.findByNameContainingIgnoreCase(name);
        return destinos.isEmpty() ? null : destinos.get(0);
    }
}