package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.user.Carer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarerRepository extends JpaRepository<Carer, Long> {
    @Query("SELECT c FROM Carer c WHERE c.id = :id AND c.active = true")
    Optional<Carer> findByIdAndActiveTrue(@Param("id") Long id);

    @Query("SELECT c FROM Carer c WHERE c.active = true")
    List<Carer> findByActiveTrue();
}