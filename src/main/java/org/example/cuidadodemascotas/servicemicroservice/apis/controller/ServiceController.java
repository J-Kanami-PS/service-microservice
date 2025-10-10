package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseList;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServiceController implements ServiceApi {

    private final ServiceService serviceService;

    @Override
    public ResponseEntity<ServiceResponseDTO> createService(ServiceRequestDTO dto) {
        log.info("POST /services - Creating new service for carer: {}, type: {}",
                dto.getCarerId(), dto.getServiceTypeId());

        ServiceResponseDTO response = serviceService.create(dto);

        log.info("Service created successfully with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ServiceResponseDTO> getServiceById(Long id) {
        log.info("GET /services/{} - Getting service by id", id);

        ServiceResponseDTO response = serviceService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseList> getServices(
            Integer page,
            Integer size,
            Long carerId,
            Long serviceTypeId,
            Double minPrice,
            Double maxPrice) {

        log.info("GET /services - Getting services with filters (page: {}, size: {}, " +
                        "carerId: {}, serviceTypeId: {}, minPrice: {}, maxPrice: {})",
                page, size, carerId, serviceTypeId, minPrice, maxPrice);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        BigDecimal minPriceBD = minPrice != null ? BigDecimal.valueOf(minPrice) : null;
        BigDecimal maxPriceBD = maxPrice != null ? BigDecimal.valueOf(maxPrice) : null;

        Page<ServiceResponseDTO> pageResult = serviceService.findByFilters(
                carerId, serviceTypeId, minPriceBD, maxPriceBD, pageNumber, pageSize);

        ServiceResponseList response = new ServiceResponseList();
        response.setContent(pageResult.getContent());
        response.setTotalElements((int) pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        log.debug("Returning {} services", response.getContent().size());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseDTO> updateService(Long id, ServiceRequestDTO dto) {
        log.info("PUT /services/{} - Updating service", id);

        ServiceResponseDTO response = serviceService.update(id, dto);

        log.info("Service updated successfully: {}", id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteService(Long id) {
        log.info("DELETE /services/{} - Deleting service (soft delete)", id);

        serviceService.delete(id);

        log.info("Service deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}