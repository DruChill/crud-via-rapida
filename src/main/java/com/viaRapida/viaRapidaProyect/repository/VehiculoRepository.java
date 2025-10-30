package com.viaRapida.viaRapidaProyect.repository;

import com.viaRapida.viaRapidaProyect.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    List<Vehiculo> findByTipo(String tipo);
    
    List<Vehiculo> findByMarca(String marca);
    
    List<Vehiculo> findByCapacidadGreaterThanEqual(Integer capacidad);
    
    @Query("SELECT v FROM Vehiculo v WHERE v.capacidad >= :capacidadMinima")
    List<Vehiculo> findVehiculosConCapacidadMinima(@Param("capacidadMinima") Integer capacidadMinima);
    
    @Query("SELECT v FROM Vehiculo v WHERE LOWER(v.tipo) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(v.marca) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(v.modelo) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Vehiculo> buscarPorTermino(@Param("query") String query);
}