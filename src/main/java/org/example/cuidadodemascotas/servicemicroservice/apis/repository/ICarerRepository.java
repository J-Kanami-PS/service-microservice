package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.user.Carer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarerRepository extends JpaRepository<Carer, Long> {
    boolean existsByIdAndActiveTrue(Long id);
}