package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.Service;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceRepository;
import org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException;
import org.example.cuidadodemascotas.servicemicroservice.utils.ServiceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@Transactional(readOnly = true)
public class ServiceService extends AbstractBaseService<Service, ServiceResponseDTO> {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Value("${pagination.size.service.list:10}")
    private int defaultPageSize;

    @Value("${pagination.size.service.search:10}")
    private int searchPageSize;

    public ServiceService(ServiceRepository repository, ServiceMapper mapper) {
        super(repository, Service.class, mapper);
        this.serviceRepository = repository;
        this.serviceMapper = mapper;
    }

    @Transactional
    public ServiceResponseDTO create(ServiceRequestDTO dto) {
        log.info("Creating service for carer: {}, type: {}", dto.getCarerId(), dto.getServiceTypeId());

        validateServiceRequest(dto);

        Service entity = serviceMapper.toEntity(dto);
        entity.setActive(true);

        Service saved = serviceRepository.save(entity);
        log.info("Service created successfully with id: {}", saved.getId());

        return serviceMapper.toDto(saved);
    }

    public ServiceResponseDTO findById(Long id) {
        log.debug("Finding service by id: {}", id);
        Service entity = findEntityById(id);
        return serviceMapper.toDto(entity);
    }

    public Page<ServiceResponseDTO> findByFilters(
            Long carerId,
            Long serviceTypeId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size) {

        log.debug("Finding services with filters - carerId: {}, serviceTypeId: {}, minPrice: {}, maxPrice: {}",
                carerId, serviceTypeId, minPrice, maxPrice);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findByFilters(
                carerId, serviceTypeId, minPrice, maxPrice, pageable);

        return entityPage.map(serviceMapper::toDto);
    }

    public Page<ServiceResponseDTO> searchByDescription(String text, int page, int size) {
        log.debug("Searching services by description: {}", text);

        int pageSize = size > 0 ? size : searchPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.searchByDescription(text, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    @Transactional
    public ServiceResponseDTO update(Long id, ServiceRequestDTO dto) {
        log.info("Updating service with id: {}", id);

        Service existing = findEntityById(id);

        validateServiceRequest(dto);

        serviceMapper.updateEntityFromDto(dto, existing);
        Service updated = serviceRepository.save(existing);

        log.info("Service updated successfully: {}", id);
        return serviceMapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Soft deleting service with id: {}", id);
        softDelete(id);
        log.info("Service deleted successfully: {}", id);
    }

    private void validateServiceRequest(ServiceRequestDTO dto) {
        if (dto.getCarerId() == null) {
            throw new IllegalArgumentException("El ID del cuidador es requerido");
        }

        if (dto.getServiceTypeId() == null) {
            throw new IllegalArgumentException("El ID del tipo de servicio es requerido");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
    }

    public long countActiveServices() {
        return serviceRepository.countByActiveTrue();
    }

    public long countActiveServicesByCarer(Long carerId) {
        return serviceRepository.countByCarerIdAndActiveTrue(carerId);
    }
}