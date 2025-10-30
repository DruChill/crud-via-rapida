package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    
    List<Ruta> findByOrigen(String origen);
    
    List<Ruta> findByDestino(String destino);
    
    Optional<Ruta> findByOrigenAndDestino(String origen, String destino);
    
    List<Ruta> findByDistanciaKmLessThanEqual(Integer distanciaMaxima);
    
    @Query("SELECT r FROM Ruta r WHERE r.distanciaKm BETWEEN :distanciaMin AND :distanciaMax")
    List<Ruta> findByRangoDistancia(@Param("distanciaMin") Integer distanciaMin, 
                                   @Param("distanciaMax") Integer distanciaMax);
    
    @Query("SELECT r FROM Ruta r WHERE LOWER(r.origen) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(r.destino) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Ruta> buscarPorTermino(@Param("query") String query);
}