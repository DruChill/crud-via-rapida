package com.viaRapida.viaRapidaProyect.serviceImpl;

import com.viaRapida.viaRapidaProyect.model.Destino;
import com.viaRapida.viaRapidaProyect.repository.DestinoRepository;
import com.viaRapida.viaRapidaProyect.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DestinoServiceImpl implements DestinoService {
    
    @Autowired
    private DestinoRepository destinoRepository;
    
    @Override
    public Destino save(Destino destino) {
        return destinoRepository.save(destino);
    }
    
    @Override
    public Optional<Destino> findById(Long id) {
        return destinoRepository.findById(id);
    }
    
    @Override
    public List<Destino> findAll() {
        return destinoRepository.findAll();
    }
    
    @Override
    public List<Destino> findByName(String name) {
        return destinoRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    public List<Destino> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return destinoRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    @Override
    public List<Destino> buscarPorTermino(String termino) {
        return destinoRepository.buscarPorTermino(termino);
    }
    
    @Override
    public Destino update(Destino destino) {
        if (destino.getId() != null && destinoRepository.existsById(destino.getId())) {
            return destinoRepository.save(destino);
        }
        throw new RuntimeException("Destino no encontrado con ID: " + destino.getId());
    }
    
    @Override
    public void deleteById(Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Destino no encontrado con ID: " + id);
        }
    }
    
    @Override
    public boolean existsById(Long id) {
        return destinoRepository.existsById(id);
    }
}