package com.viaRapida.viaRapidaProyect.serviceImpl;

import com.viaRapida.viaRapidaProyect.model.Cliente;
import com.viaRapida.viaRapidaProyect.repository.ClienteRepository;
import com.viaRapida.viaRapidaProyect.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    @Override
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    @Override
    public List<Cliente> buscarPorTermino(String termino) {
        return clienteRepository.buscarPorTermino(termino);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
    
    @Override
    public Cliente update(Cliente cliente) {
        if (cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
            return clienteRepository.save(cliente);
        }
        throw new RuntimeException("Cliente no encontrado con ID: " + cliente.getId());
    }
    
    @Override
    public void deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }
    
    @Override
    public boolean existsById(Long id) {
        return clienteRepository.existsById(id);
    }
}