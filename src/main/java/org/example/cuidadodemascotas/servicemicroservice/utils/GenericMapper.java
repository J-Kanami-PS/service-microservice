package org.example.cuidadodemascotas.servicemicroservice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericMapper<E, D> {

    @Autowired
    protected ModelMapper modelMapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    protected GenericMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public D toDto(E entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, dtoClass);
    }

    public E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, entityClass);
    }
}