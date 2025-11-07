package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.base.AbstractEntity;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.IBaseRepository;
import org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException;
import org.example.cuidadodemascotas.servicemicroservice.utils.GenericMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
public abstract class AbstractBaseService<E extends AbstractEntity, D> {

    protected final IBaseRepository<E> repository;
    protected final Class<E> entityClass;
    protected final GenericMapper<E, D> mapper;

    protected AbstractBaseService(
            IBaseRepository<E> repository,
            Class<E> entityClass,
            GenericMapper<E, D> mapper) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.mapper = mapper;
    }

    protected E findEntityById(Long id) {
        log.debug("Finding {} by id: {}", entityClass.getSimpleName(), id);
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException(id, entityClass));
    }

    protected D save(E entity) {
        log.debug("Saving {}", entityClass.getSimpleName());
        E savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    protected Page<D> findAllPaged(int page, int size) {
        log.debug("Finding all {} with pagination (page: {}, size: {})",
                entityClass.getSimpleName(), page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<E> entityPage = repository.findByActiveTrue(pageable);
        return entityPage.map(mapper::toDto);
    }

    protected void softDelete(Long id) {
        log.debug("Soft deleting {} with id: {}", entityClass.getSimpleName(), id);
        E entity = findEntityById(id);
        entity.setActive(false);
        repository.save(entity);
    }
}