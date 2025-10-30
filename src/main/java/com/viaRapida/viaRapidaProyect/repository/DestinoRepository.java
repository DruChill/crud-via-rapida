package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    
    List<Destino> findByNameContainingIgnoreCase(String name);
    
    List<Destino> findByPriceLessThanEqual(BigDecimal price);
    
    List<Destino> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT d FROM Destino d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Destino> buscarPorTermino(@Param("query") String query);
}