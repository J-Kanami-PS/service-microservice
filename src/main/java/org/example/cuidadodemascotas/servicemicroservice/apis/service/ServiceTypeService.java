package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceTypeRepository;
import org.example.cuidadodemascotas.servicemicroservice.utils.ServiceTypeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ServiceTypeService {

    private final ServiceTypeRepository repository;
    private final ServiceTypeMapper mapper;

    @Value("${pagination.size.service-type.list:20}")
    private int defaultPageSize;

    public ServiceTypeService(ServiceTypeRepository repository, ServiceTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ServiceTypeResponseDTO create(ServiceTypeRequestDTO dto) {
        log.info("Creating service type: {}", dto.getName());

        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de servicio con el nombre: " + dto.getName());
        }

        ServiceType entity = mapper.toEntity(dto);
        ServiceType saved = repository.save(entity);

        log.info("Service type created successfully with id: {}", saved.getId());
        return mapper.toDto(saved);
    }

    public ServiceTypeResponseDTO findById(Long id) {
        log.debug("Finding service type by id: {}", id);
        ServiceType entity = repository.findById(id)
                .orElseThrow(() -> new org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException(
                        id, ServiceType.class));
        return mapper.toDto(entity);
    }

    public Page<ServiceTypeResponseDTO> findAll(int page, int size, String sort) {
        log.debug("Finding all service types (page: {}, size: {}, sort: {})", page, size, sort);

        int pageSize = size > 0 ? size : defaultPageSize;

        // Determinar campo de ordenamiento
        Sort sortOrder;
        if (sort == null || sort.isBlank()) {
            sortOrder = Sort.by("name").ascending(); // orden por defecto
        } else if (sort.startsWith("-")) {
            sortOrder = Sort.by(sort.substring(1)).descending();
        } else {
            sortOrder = Sort.by(sort).ascending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sortOrder);

        Page<ServiceType> entityPage = repository.findAll(pageable);
        return entityPage.map(mapper::toDto);
    }

    public Page<ServiceTypeResponseDTO> searchByName(String name, int page, int size) {
        log.debug("Searching service types by name: {}", name);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<ServiceType> entityPage = repository.searchByName(name, pageable);
        return entityPage.map(mapper::toDto);
    }

    @Transactional
    public ServiceTypeResponseDTO update(Long id, ServiceTypeRequestDTO dto) {
        log.info("Updating service type with id: {}", id);

        ServiceType existing = repository.findById(id)
                .orElseThrow(() -> new org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException(
                        id, ServiceType.class));

        if (repository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe otro tipo de servicio con el nombre: " + dto.getName());
        }

        mapper.updateEntityFromDto(dto, existing);
        ServiceType updated = repository.save(existing);

        log.info("Service type updated successfully: {}", id);
        return mapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting service type with id: {}", id);

        ServiceType entity = repository.findById(id)
                .orElseThrow(() -> new org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException(
                        id, ServiceType.class));

        repository.delete(entity);
        log.info("Service type deleted successfully: {}", id);
    }

    public ServiceTypeResponseDTO findByName(String name) {
        log.debug("Finding service type by name: {}", name);
        ServiceType entity = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException(
                        "ServiceType no encontrado con nombre: " + name));
        return mapper.toDto(entity);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public java.util.List<ServiceTypeResponseDTO> findAllOrdered() {
        log.debug("Finding all service types ordered by name");
        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}