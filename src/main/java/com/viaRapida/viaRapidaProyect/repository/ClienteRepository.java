package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);
    
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Cliente> buscarPorTermino(@Param("query") String query);
    
    boolean existsByEmail(String email);
}