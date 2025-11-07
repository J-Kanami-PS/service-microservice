package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypePageResponse;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.ServiceTypeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceTypeController implements ServiceTypeApi {

    private final ServiceTypeService serviceTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<ServiceTypeResponseDTO> createServiceType(ServiceTypeRequestDTO serviceTypeRequestDTO) {
        log.info("POST /service-types - Creating new service type: {}", serviceTypeRequestDTO.getName());
        ServiceTypeResponseDTO response = serviceTypeService.create(serviceTypeRequestDTO);
        log.info("Service type created successfully with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ServiceTypeResponseDTO> getServiceTypeById(Long id) {
        log.info("GET /service-types/{} - Getting service type by id", id);
        ServiceTypeResponseDTO response = serviceTypeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceTypePageResponse> getServiceTypes(
            Integer page,
            Integer size
    ) {
        log.info("GET /service-types - Getting service types (page: {}, size: {})", page, size);
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 20;
        Page<ServiceTypeResponseDTO> pageResult = serviceTypeService.findAll(pageNumber, pageSize, null);
        ServiceTypePageResponse response = new ServiceTypePageResponse();
        response.setContent(pageResult.getContent());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());
        log.debug("Returning {} service types", response.getContent().size());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<ServiceTypeResponseDTO> updateServiceType(
            Long id,
            ServiceTypeRequestDTO serviceTypeRequestDTO
    ) {
        log.info("PUT /service-types/{} - Updating service type", id);
        ServiceTypeResponseDTO response = serviceTypeService.update(id, serviceTypeRequestDTO);
        log.info("Service type updated successfully: {}", id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void> deleteServiceType(Long id) {
        log.info("DELETE /service-types/{} - Deleting service type", id);
        serviceTypeService.delete(id);
        log.info("Service type deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}