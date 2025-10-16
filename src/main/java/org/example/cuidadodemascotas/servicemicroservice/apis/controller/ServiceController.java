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
            Integer size) {

        log.info("GET /services - Getting all services (page: {}, size: {})", page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Page<ServiceResponseDTO> pageResult = serviceService.findAll(pageNumber, pageSize);

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
    public ResponseEntity<ServiceResponseList> getServicesByCarerId(
            Long carerId,
            Integer page,
            Integer size) {

        log.info("GET /services/carer/{} - Getting services by carer (page: {}, size: {})",
                carerId, page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Page<ServiceResponseDTO> pageResult = serviceService.findByCarerId(carerId, pageNumber, pageSize);

        ServiceResponseList response = new ServiceResponseList();
        response.setContent(pageResult.getContent());
        response.setTotalElements((int) pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        log.debug("Returning {} services for carer {}", response.getContent().size(), carerId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseList> getServicesByServiceTypeId(
            Long serviceTypeId,
            Integer page,
            Integer size) {

        log.info("GET /services/type/{} - Getting services by type (page: {}, size: {})",
                serviceTypeId, page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Page<ServiceResponseDTO> pageResult = serviceService.findByServiceTypeId(serviceTypeId, pageNumber, pageSize);

        ServiceResponseList response = new ServiceResponseList();
        response.setContent(pageResult.getContent());
        response.setTotalElements((int) pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        log.debug("Returning {} services for type {}", response.getContent().size(), serviceTypeId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseList> getServicesByPriceRange(
            Double minPrice,
            Double maxPrice,
            Integer page,
            Integer size) {

        log.info("GET /services/price-range/{}/{} - Getting services by price range (page: {}, size: {})",
                minPrice, maxPrice, page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        BigDecimal minPriceBD = BigDecimal.valueOf(minPrice);
        BigDecimal maxPriceBD = BigDecimal.valueOf(maxPrice);

        Page<ServiceResponseDTO> pageResult = serviceService.findByPriceRange(
                minPriceBD, maxPriceBD, pageNumber, pageSize);

        ServiceResponseList response = new ServiceResponseList();
        response.setContent(pageResult.getContent());
        response.setTotalElements((int) pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        log.debug("Returning {} services in price range {}-{}",
                response.getContent().size(), minPrice, maxPrice);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ServiceResponseList> getServicesByCarerAndType(
            Long carerId,
            Long serviceTypeId,
            Integer page,
            Integer size) {

        log.info("GET /services/carer/{}/type/{} - Getting services by carer and type (page: {}, size: {})",
                carerId, serviceTypeId, page, size);

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Page<ServiceResponseDTO> pageResult = serviceService.findByCarerAndType(
                carerId, serviceTypeId, pageNumber, pageSize);

        ServiceResponseList response = new ServiceResponseList();
        response.setContent(pageResult.getContent());
        response.setTotalElements((int) pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        log.debug("Returning {} services for carer {} and type {}",
                response.getContent().size(), carerId, serviceTypeId);
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