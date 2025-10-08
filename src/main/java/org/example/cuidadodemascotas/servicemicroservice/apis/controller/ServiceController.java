package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascota.commons.entities.user.Carer;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseList;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.ServiceService;
import org.example.cuidadodemascotas.servicemicroservice.utils.EntityMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceController implements ServiceApi {

    private final ServiceService serviceService;

    @Override
    public ResponseEntity<ServiceResponseDTO> createService(ServiceRequestDTO serviceRequestDTO) {
        log.info("POST /services - Creating new service for carer: {}, type: {}",
                serviceRequestDTO.getCarerId(), serviceRequestDTO.getServiceTypeId());

        try {
            org.example.cuidadodemascota.commons.entities.service.Service service =
                    new org.example.cuidadodemascota.commons.entities.service.Service();

            // Configurar carer
            Carer carer = new Carer();
            EntityMapper.setEntityId(carer, serviceRequestDTO.getCarerId());
            service.setCarer(carer);

            // Configurar service type
            ServiceType serviceType = new ServiceType();
            EntityMapper.setEntityId(serviceType, serviceRequestDTO.getServiceTypeId());
            service.setServiceType(serviceType);

            // Configurar otros campos
            service.setDescription(serviceRequestDTO.getDescription());
            service.setPrice(BigDecimal.valueOf(serviceRequestDTO.getPrice()));

            org.example.cuidadodemascota.commons.entities.service.Service savedService =
                    serviceService.create(service);
            ServiceResponseDTO response = mapToResponseDTO(savedService);

            log.info("Service created successfully with id: {}", EntityMapper.getEntityId(savedService));
            return ResponseEntity.status(201).body(response);

        } catch (IllegalArgumentException e) {
            log.warn("Error creating service: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error creating service", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<ServiceResponseDTO> getServiceById(Long id) {
        log.info("GET /services/{} - Getting service by id", id);

        try {
            return serviceService.findById(id)
                    .map(this::mapToResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error getting service by id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<ServiceResponseList> getServices(
            Integer page,
            Integer size,
            Long carerId,
            Long serviceTypeId,
            Double minPrice,
            Double maxPrice) {

        log.info("GET /services - Getting services with filters (page: {}, size: {}, carerId: {}, serviceTypeId: {}, minPrice: {}, maxPrice: {})",
                page, size, carerId, serviceTypeId, minPrice, maxPrice);

        try {
            // Parámetros de paginación por defecto
            int pageNumber = (page != null && page >= 0) ? page : 0;
            int pageSize = (size != null && size > 0) ? size : 10;

            // Convertir Double a BigDecimal
            BigDecimal minPriceBD = minPrice != null ? BigDecimal.valueOf(minPrice) : null;
            BigDecimal maxPriceBD = maxPrice != null ? BigDecimal.valueOf(maxPrice) : null;

            // 🔹 Llamar al servicio con paginación real
            var pageResult = serviceService.findByFilters(
                    carerId, serviceTypeId, minPriceBD, maxPriceBD, pageNumber, pageSize
            );

            // Mapear a DTOs
            List<ServiceResponseDTO> content = pageResult.getContent()
                    .stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());

            // Crear respuesta paginada
            ServiceResponseList response = new ServiceResponseList();
            response.setContent(content);
            response.setTotalElements(Integer.valueOf((int) pageResult.getTotalElements()));
            response.setTotalPages(pageResult.getTotalPages());
            response.setSize(pageResult.getSize());
            response.setNumber(pageResult.getNumber());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error getting services", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<ServiceResponseDTO> updateService(Long id, ServiceRequestDTO serviceRequestDTO) {
        log.info("PUT /services/{} - Updating service", id);

        try {
            org.example.cuidadodemascota.commons.entities.service.Service serviceDetails =
                    new org.example.cuidadodemascota.commons.entities.service.Service();

            // Configurar campos a actualizar
            if (serviceRequestDTO.getCarerId() != null) {
                Carer carer = new Carer();
                EntityMapper.setEntityId(carer, serviceRequestDTO.getCarerId());
                serviceDetails.setCarer(carer);
            }

            if (serviceRequestDTO.getServiceTypeId() != null) {
                ServiceType serviceType = new ServiceType();
                EntityMapper.setEntityId(serviceType, serviceRequestDTO.getServiceTypeId());
                serviceDetails.setServiceType(serviceType);
            }

            serviceDetails.setDescription(serviceRequestDTO.getDescription());
            if (serviceRequestDTO.getPrice() != null) {
                serviceDetails.setPrice(BigDecimal.valueOf(serviceRequestDTO.getPrice()));
            }

            org.example.cuidadodemascota.commons.entities.service.Service updatedService =
                    serviceService.update(id, serviceDetails);
            ServiceResponseDTO response = mapToResponseDTO(updatedService);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Error updating service {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating service: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteService(Long id) {
        log.info("DELETE /services/{} - Deleting service", id);

        try {
            serviceService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            log.warn("Error deleting service {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting service: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ========== MAPPER METHOD ==========
    private ServiceResponseDTO mapToResponseDTO(org.example.cuidadodemascota.commons.entities.service.Service service) {
        ServiceResponseDTO dto = new ServiceResponseDTO();

        dto.setId(EntityMapper.getEntityId(service));
        dto.setCarerId(EntityMapper.getEntityId(service.getCarer()));
        dto.setServiceTypeId(EntityMapper.getEntityId(service.getServiceType()));
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice().doubleValue());

        // Convertir fechas
        dto.setCreatedAt(EntityMapper.toOffsetDateTime(EntityMapper.getCreatedAt(service)));
        dto.setUpdatedAt(EntityMapper.toOffsetDateTime(EntityMapper.getUpdatedAt(service)));

        dto.setActive(EntityMapper.getActive(service));

        return dto;
    }
}