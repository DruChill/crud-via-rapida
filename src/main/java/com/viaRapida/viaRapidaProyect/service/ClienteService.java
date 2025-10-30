package com.viaRapida.viaRapidaProyect.service;

import com.viaRapida.viaRapidaProyect.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    
    Cliente save(Cliente cliente);
    
    Optional<Cliente> findById(Long id);
    
    List<Cliente> findAll();
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> buscarPorTermino(String termino);
    
    boolean existsByEmail(String email);
    
    Cliente update(Cliente cliente);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}