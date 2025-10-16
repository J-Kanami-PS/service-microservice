package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.Service;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceRepository;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceTypeRepository;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ICarerRepository;
import org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException;
import org.example.cuidadodemascotas.servicemicroservice.utils.ServiceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Transactional(readOnly = true)
public class ServiceService extends AbstractBaseService<Service, ServiceResponseDTO> {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final ICarerRepository iCarerRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    @Value("${pagination.size.service.list:10}")
    private int defaultPageSize;

    @Value("${pagination.size.service.search:10}")
    private int searchPageSize;

    public ServiceService(
            ServiceRepository repository,
            ServiceMapper mapper,
            ICarerRepository iCarerRepository,
            ServiceTypeRepository serviceTypeRepository) {
        super(repository, Service.class, mapper);
        this.serviceRepository = repository;
        this.serviceMapper = mapper;
        this.iCarerRepository = iCarerRepository;
        this.serviceTypeRepository = serviceTypeRepository;
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

    // ==================== MÉTODOS - PATH PARAMETERS ====================

    /**
     * Obtener todos los servicios con paginación
     * GET /services?page=0&size=10
     */
    public Page<ServiceResponseDTO> findAll(int page, int size) {
        log.debug("Finding all services (page: {}, size: {})", page, size);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findAllServices(pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    /**
     * Obtener servicios por cuidador con paginación
     * GET /services/carer/{carerId}?page=0&size=10
     */
    public Page<ServiceResponseDTO> findByCarerId(Long carerId, int page, int size) {
        log.debug("Finding services by carer id: {} (page: {}, size: {})", carerId, page, size);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findByCarerIdPaged(carerId, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    /**
     * Obtener servicios por tipo de servicio con paginación
     * GET /services/type/{serviceTypeId}?page=0&size=10
     */
    public Page<ServiceResponseDTO> findByServiceTypeId(Long serviceTypeId, int page, int size) {
        log.debug("Finding services by service type id: {} (page: {}, size: {})", serviceTypeId, page, size);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findByServiceTypeIdPaged(serviceTypeId, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    /**
     * Obtener servicios por rango de precio con paginación
     * GET /services/price-range/{minPrice}/{maxPrice}?page=0&size=10
     */
    public Page<ServiceResponseDTO> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
        log.debug("Finding services by price range: {} - {} (page: {}, size: {})", minPrice, maxPrice, page, size);

        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Precio mínimo y máximo son requeridos");
        }

        if (minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Precio mínimo no puede ser mayor que precio máximo");
        }

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findByPriceRangePaged(minPrice, maxPrice, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    /**
     * Obtener servicios por cuidador y tipo de servicio con paginación
     * GET /services/carer/{carerId}/type/{serviceTypeId}?page=0&size=10
     */
    public Page<ServiceResponseDTO> findByCarerAndType(Long carerId, Long serviceTypeId, int page, int size) {
        log.debug("Finding services by carer {} and type {} (page: {}, size: {})",
                carerId, serviceTypeId, page, size);

        int pageSize = size > 0 ? size : defaultPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.findByCarerIdAndServiceTypeIdPaged(
                carerId, serviceTypeId, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    /**
     * Búsqueda por descripción (mantiene funcionalidad anterior)
     */
    public Page<ServiceResponseDTO> searchByDescription(String text, int page, int size) {
        log.debug("Searching services by description: {}", text);

        int pageSize = size > 0 ? size : searchPageSize;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Service> entityPage = serviceRepository.searchByDescription(text, pageable);
        return entityPage.map(serviceMapper::toDto);
    }

    // ==================== MÉTODOS PARA MANTENER COMPATIBILIDAD ====================

    /**
     * Método antiguo con filtros opcionales
     * Se mantiene por compatibilidad pero ya no se usa en endpoints
     */
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

    // ==================== MÉTODOS AUXILIARES ====================

    public long countActiveServices() {
        return serviceRepository.countByActiveTrue();
    }

    public long countActiveServicesByCarer(Long carerId) {
        return serviceRepository.countByCarerIdAndActiveTrue(carerId);
    }

    public long countServicesByCarer(Long carerId) {
        return serviceRepository.countByCarerId(carerId);
    }

    public long countServicesByServiceType(Long serviceTypeId) {
        return serviceRepository.countByServiceTypeId(serviceTypeId);
    }

    public long countServicesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return serviceRepository.countByPriceRange(minPrice, maxPrice);
    }

    public List<ServiceResponseDTO> findByCarerId(Long carerId) {
        log.debug("Finding services by carer id: {}", carerId);
        List<Service> services = serviceRepository.findByCarerIdAndActiveTrue(carerId);
        return services.stream()
                .map(serviceMapper::toDto)
                .toList();
    }

    public List<ServiceResponseDTO> findByServiceTypeId(Long serviceTypeId) {
        log.debug("Finding services by service type id: {}", serviceTypeId);
        List<Service> services = serviceRepository.findByServiceTypeIdAndActiveTrue(serviceTypeId);
        return services.stream()
                .map(serviceMapper::toDto)
                .toList();
    }
}