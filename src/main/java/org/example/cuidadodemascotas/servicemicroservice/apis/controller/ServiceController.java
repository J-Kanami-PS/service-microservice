package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServicePageResponse;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceController implements ServiceApi {

    private final ServiceService serviceService;

    /*@Override
    public ResponseEntity<ServicePageResponse> getServices(
            Long carerId,
            Long serviceTypeId,
            Double minPrice,
            Double maxPrice,
            Integer page,
            Integer size
    ) {
        log.info("GET /services - Filters: carerId={}, typeId={}, price={}-{}, page={}, size={}",
                carerId, serviceTypeId, minPrice, maxPrice, page, size);
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;
        BigDecimal minPriceBd = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
        BigDecimal maxPriceBd = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : null;
        Page<ServiceResponseDTO> pageResult = serviceService.findByFilters(
                carerId, serviceTypeId, minPriceBd, maxPriceBd, pageNumber, pageSize
        );
        ServicePageResponse response = new ServicePageResponse();
        response.setContent(pageResult.getContent());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());
        return ResponseEntity.ok(response);
    }*/

    @Override
    public ResponseEntity<ServicePageResponse> getServices(
            Long carerId,
            Long serviceTypeId,
            Double minPrice,
            Double maxPrice,
            Integer page,
            Integer size
    ) {
        log.info("GET /services - Filters: carerId={}, typeId={}, price={}-{}, page={}, size={}",
                carerId, serviceTypeId, minPrice, maxPrice, page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;
        BigDecimal minPriceBd = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
        BigDecimal maxPriceBd = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : null;

        // Llamamos al servicio (que ahora ya devuelve los DTOs completos con serviceType)
        Page<ServiceResponseDTO> pageResult = serviceService.findByFilters(
                carerId, serviceTypeId, minPriceBd, maxPriceBd, pageNumber, pageSize
        );

        // Armamos la respuesta limpia
        ServicePageResponse response = new ServicePageResponse();
        response.setContent(pageResult.getContent());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServicePageResponse> searchServicesByText(
            String text,
            Integer page,
            Integer size
    ) {
        log.info("GET /services/search - text: {}, page: {}, size: {}", text, page, size);
        // Conversión de tipos
        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;
        Page<ServiceResponseDTO> pageResult = serviceService.searchByDescription(text, pageNumber, pageSize);
        // Convertir Page<DTO> a ServicePageResponse
        ServicePageResponse response = new ServicePageResponse();
        response.setContent(pageResult.getContent());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseDTO> getServiceById(Long id) {
        log.info("GET /services/{}", id);
        ServiceResponseDTO response = serviceService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_CARER')")
    @Override
    public ResponseEntity<ServiceResponseDTO> createService(ServiceRequestDTO serviceRequestDTO) {
        log.info("POST /services - Creating service for carer: {}, type: {}",
                serviceRequestDTO.getCarerId(), serviceRequestDTO.getServiceTypeId());
        ServiceResponseDTO created = serviceService.create(serviceRequestDTO);
        log.info("Service created successfully with id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ROLE_CARER')")
    @Override
    public ResponseEntity<ServiceResponseDTO> updateService(
            Long id,
            ServiceRequestDTO serviceRequestDTO
    ) {
        log.info("PUT /services/{}", id);
        ServiceResponseDTO updated = serviceService.update(id, serviceRequestDTO);
        log.info("Service updated successfully: {}", id);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ROLE_CARER')")
    @Override
    public ResponseEntity<Void> deleteService(Long id) {
        log.info("DELETE /services/{} - Soft delete", id);
        serviceService.delete(id);
        log.info("Service deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}