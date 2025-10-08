package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceRepository;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    // ========== CREATE ==========
    @Transactional
    public org.example.cuidadodemascota.commons.entities.service.Service create(
            org.example.cuidadodemascota.commons.entities.service.Service service) {

        Long carerId = getEntityId(service.getCarer());
        log.info("Creating service for carer: {}", carerId);

        validateCarerAndServiceType(service.getCarer(), service.getServiceType());

        if (service.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }

        service.setActive(true);
        return serviceRepository.save(service);
    }

    // ========== READ ==========
    public Page<org.example.cuidadodemascota.commons.entities.service.Service> findAllActive(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return serviceRepository.findByActiveTrue(pageable);
    }

    public Optional<org.example.cuidadodemascota.commons.entities.service.Service> findById(Long id) {
        return serviceRepository.findByIdAndActiveTrue(id);
    }

    public List<org.example.cuidadodemascota.commons.entities.service.Service> findByCarerId(Long carerId) {
        return serviceRepository.findByCarerIdAndActiveTrue(carerId);
    }

    public List<org.example.cuidadodemascota.commons.entities.service.Service> findByServiceTypeId(Long serviceTypeId) {
        return serviceRepository.findByServiceTypeIdAndActiveTrue(serviceTypeId);
    }

    public Page<org.example.cuidadodemascota.commons.entities.service.Service> findByFilters(
            Long carerId, Long serviceTypeId, BigDecimal minPrice, BigDecimal maxPrice,
            int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return serviceRepository.findByFilters(carerId, serviceTypeId, minPrice, maxPrice, pageable);
    }

    // ========== UPDATE ==========
    @Transactional
    public org.example.cuidadodemascota.commons.entities.service.Service update(
            Long id, org.example.cuidadodemascota.commons.entities.service.Service serviceDetails) {

        org.example.cuidadodemascota.commons.entities.service.Service existingService =
                serviceRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con id: " + id));

        if (serviceDetails.getServiceType() != null) {
            existingService.setServiceType(serviceDetails.getServiceType());
        }
        if (serviceDetails.getCarer() != null) {
            existingService.setCarer(serviceDetails.getCarer());
        }
        if (serviceDetails.getDescription() != null) {
            existingService.setDescription(serviceDetails.getDescription());
        }
        if (serviceDetails.getPrice() != null) {
            if (serviceDetails.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
            }
            existingService.setPrice(serviceDetails.getPrice());
        }

        return serviceRepository.save(existingService);
    }

    // ========== DELETE LÓGICO ==========
    @Transactional
    public void delete(Long id) {
        org.example.cuidadodemascota.commons.entities.service.Service service =
                serviceRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con id: " + id));

        service.setActive(false);
        serviceRepository.save(service);
    }

    // ========== VALIDATION METHODS ==========
    private void validateCarerAndServiceType(
            Object carer,
            ServiceType serviceType) {

        Long carerId = getEntityId(carer);
        if (carerId == null) {
            throw new IllegalArgumentException("Carer es requerido");
        }

        Long serviceTypeId = getEntityId(serviceType);
        if (serviceTypeId == null) {
            throw new IllegalArgumentException("ServiceType es requerido");
        }

        ServiceType existingServiceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de servicio no encontrado con id: " + serviceTypeId));
    }

    // ========== REFLECTION HELPER ==========
    private Long getEntityId(Object entity) {
        if (entity == null) {
            return null;
        }
        try {
            // Intentar obtener el ID via reflection
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (Long) getIdMethod.invoke(entity);
        } catch (Exception e) {
            log.warn("No se pudo obtener el ID del entity: {}", entity.getClass().getSimpleName());
            return null;
        }
    }

    // ========== COUNT METHODS ==========
    public long countActiveServices() {
        return serviceRepository.countByActiveTrue();
    }

    public long countActiveServicesByCarer(Long carerId) {
        return serviceRepository.countByCarerIdAndActiveTrue(carerId);
    }
}