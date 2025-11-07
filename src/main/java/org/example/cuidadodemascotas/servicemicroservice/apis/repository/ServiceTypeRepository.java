package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findByNameIgnoreCase(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT st FROM ServiceType st WHERE LOWER(st.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<ServiceType> searchByName(@Param("name") String name, Pageable pageable);

    Page<ServiceType> findAllByOrderByNameAsc(Pageable pageable);

    List<ServiceType> findAllByOrderByNameAsc();
}