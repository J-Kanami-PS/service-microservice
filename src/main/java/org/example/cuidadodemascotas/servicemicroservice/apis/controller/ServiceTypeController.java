package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseList;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.ServiceTypeService;
import org.example.cuidadodemascotas.servicemicroservice.utils.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceTypeController implements ServiceTypeApi {

    private final ServiceTypeService serviceTypeService;

    @Override
    public ResponseEntity<ServiceTypeResponseDTO> createServiceType(ServiceTypeRequestDTO serviceTypeRequestDTO) {
        log.info("POST /service-types - Creating new service type: {}", serviceTypeRequestDTO.getName());

        try {
            ServiceType serviceType = new ServiceType();
            EntityMapper.setServiceTypeName(serviceType, serviceTypeRequestDTO.getName());

            ServiceType savedServiceType = serviceTypeService.create(serviceType);
            ServiceTypeResponseDTO response = mapToResponseDTO(savedServiceType);

            log.info("Service type created successfully with id: {}", EntityMapper.getEntityId(savedServiceType));
            return ResponseEntity.status(201).body(response);

        } catch (IllegalArgumentException e) {
            log.warn("Error creating service type: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error creating service type", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<ServiceTypeResponseDTO> getServiceTypeById(Long id) {
        log.info("GET /service-types/{} - Getting service type by id", id);

        try {
            return serviceTypeService.findById(id)
                    .map(this::mapToResponseDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error getting service type by id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<ServiceTypeResponseList> getServiceTypes(Integer page, Integer size, String sort) {
        log.info("GET /service-types - page: {}, size: {}", page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Page<ServiceType> pageResult = serviceTypeService.findAllPaged(pageNumber, pageSize);

        List<ServiceTypeResponseDTO> content = pageResult.getContent()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();

        ServiceTypeResponseList response = new ServiceTypeResponseList();
        response.setContent(content);
        response.setTotalElements(Integer.valueOf((int) pageResult.getTotalElements()));
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceTypeResponseDTO> updateServiceType(Long id, ServiceTypeRequestDTO serviceTypeRequestDTO) {
        log.info("PUT /service-types/{} - Updating service type", id);

        try {
            ServiceType serviceTypeDetails = new ServiceType();
            EntityMapper.setServiceTypeName(serviceTypeDetails, serviceTypeRequestDTO.getName());

            ServiceType updatedServiceType = serviceTypeService.update(id, serviceTypeDetails);
            ServiceTypeResponseDTO response = mapToResponseDTO(updatedServiceType);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Error updating service type {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating service type: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteServiceType(Long id) {
        log.info("DELETE /service-types/{} - Deleting service type", id);

        try {
            serviceTypeService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            log.warn("Error deleting service type {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting service type: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ========== MAPPER METHOD ==========
    private ServiceTypeResponseDTO mapToResponseDTO(ServiceType serviceType) {
        ServiceTypeResponseDTO dto = new ServiceTypeResponseDTO();
        dto.setId(EntityMapper.getEntityId(serviceType));
        dto.setName(EntityMapper.getServiceTypeName(serviceType));
        return dto;
    }
}