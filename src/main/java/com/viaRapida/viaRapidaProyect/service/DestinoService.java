package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.Destino;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DestinoService {
    
    Destino save(Destino destino);
    
    Optional<Destino> findById(Long id);
    
    List<Destino> findAll();
    
    List<Destino> findByName(String name);
    
    List<Destino> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Destino> buscarPorTermino(String termino);
    
    Destino update(Destino destino);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}