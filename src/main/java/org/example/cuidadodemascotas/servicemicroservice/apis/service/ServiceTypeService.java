package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.IServiceTypeRepository;
import org.example.cuidadodemascotas.servicemicroservice.utils.ServiceTypeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ServiceTypeService {

    private final IServiceTypeRepository repository;
    private final ServiceTypeMapper mapper;
    private final RedisCacheManager cacheManager;

    @Value("${pagination.size.service-type.list:20}")
    private int defaultPageSize;

    public ServiceTypeService(IServiceTypeRepository repository, ServiceTypeMapper mapper, RedisCacheManager cacheManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    // Limpia solo la lista completa al crear
    @CacheEvict(value = "service_types", key = "'byId_' + #id")
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

    // Cachea por ID
    @Cacheable(value = "service_types", key = "'byId_' + #id")
    public ServiceTypeResponseDTO findById(Long id) {
        log.debug("Finding service type by id: {}", id);
        ServiceType entity = repository.findById(id)
                .orElseThrow(() -> new org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException(
                        id, ServiceType.class));
        return mapper.toDto(entity);
    }

    /**
     * Obtiene todos los tipos de servicio con paginación y ordenamiento.
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public Page<ServiceTypeResponseDTO> findAll(int page, int size, String sort) {
        log.debug("Finding all service types (page: {}, size: {}, sort: {})", page, size, sort);
        int pageSize = size > 0 ? size : defaultPageSize;
        Sort sortOrder;
        if (sort == null || sort.isBlank()) {
            sortOrder = Sort.by("name").ascending();
        } else if (sort.startsWith("-")) {
            sortOrder = Sort.by(sort.substring(1)).descending();
        } else {
            sortOrder = Sort.by(sort).ascending();
        }
        Pageable pageable = PageRequest.of(page, pageSize, sortOrder);
        Page<ServiceType> entityPage = repository.findAll(pageable);

        entityPage.forEach(st->{
            cacheManager.getCache("service_types").put("byId_" + st.getId(), mapper.toDto(st));
            log.info("Tipo de servicio cacheado con ID: {}", st.getId());
        });
        log.info("Todos los tipos de servicio cacheados.");

        return entityPage.map(mapper::toDto);
    }

    public Page<ServiceTypeResponseDTO> searchByName(String name, int page, int size) {
        log.debug("Searching service types by name: {}", name);
        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<ServiceType> entityPage = repository.searchByName(name, pageable);
        return entityPage.map(mapper::toDto);
    }

    // Actualiza el item específico Y limpia la lista
    @CachePut(value = "service_types", key = "'byId_' + #id")
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

    // Elimina ambos: el item específico Y la lista
    @CacheEvict(value = "service_types", key = "'byId_' + #id")
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

//    @Cacheable(value = "service_types", key = "'all_ordered'")
    public java.util.List<ServiceTypeResponseDTO> findAllOrdered() {
        log.debug("Finding all service types ordered by name");
        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}