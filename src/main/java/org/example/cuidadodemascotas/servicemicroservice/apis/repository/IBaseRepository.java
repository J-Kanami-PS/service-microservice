package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.base.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IBaseRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {

    Optional<E> findByIdAndActiveTrue(Long id);

    Page<E> findByActiveTrue(Pageable pageable);

    List<E> findByActiveTrue();
}