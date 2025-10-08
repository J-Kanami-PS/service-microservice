package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    // Encontrar por nombre (case insensitive)
    Optional<ServiceType> findByNameIgnoreCase(String name);

    // Verificar si existe por nombre (para validaciones)
    boolean existsByName(String name);

    Page<ServiceType> findAllByOrderByNameAsc(Pageable pageable);

    // Buscar por nombre like (para búsquedas)
    @Query("SELECT st FROM ServiceType st WHERE LOWER(st.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ServiceType> findByNameContainingIgnoreCase(String name);

    // Encontrar todos ordenados por nombre
    List<ServiceType> findAllByOrderByNameAsc();
}