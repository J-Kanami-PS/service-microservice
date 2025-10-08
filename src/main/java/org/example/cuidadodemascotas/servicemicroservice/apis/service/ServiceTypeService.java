package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    // ========== CREATE ==========
    @Transactional
    public ServiceType create(ServiceType serviceType) {
        log.info("Creating service type: {}", serviceType.getName());

        // Validar que no exista otro con el mismo nombre
        if (serviceTypeRepository.existsByName(serviceType.getName())) {
            throw new IllegalArgumentException("Ya existe un tipo de servicio con el nombre: " + serviceType.getName());
        }

        return serviceTypeRepository.save(serviceType);
    }

    // ========== READ ==========
    public Page<ServiceType> findAllPaged(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return serviceTypeRepository.findAllByOrderByNameAsc(pageable);
    }

    public Optional<ServiceType> findById(Long id) {
        log.info("Finding service type by id: {}", id);
        return serviceTypeRepository.findById(id);
    }

    public Optional<ServiceType> findByName(String name) {
        log.info("Finding service type by name: {}", name);
        return serviceTypeRepository.findByNameIgnoreCase(name);
    }

    public List<ServiceType> findByNameContaining(String name) {
        log.info("Searching service types containing: {}", name);
        return serviceTypeRepository.findByNameContainingIgnoreCase(name);
    }

    // ========== UPDATE ==========
    @Transactional
    public ServiceType update(Long id, ServiceType serviceTypeDetails) {
        log.info("Updating service type with id: {}", id);

        ServiceType existingServiceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de servicio no encontrado con id: " + id));

        // Validar nombre único (excluyendo el actual)
        if (serviceTypeRepository.existsByName(serviceTypeDetails.getName()) &&
                !existingServiceType.getName().equals(serviceTypeDetails.getName())) {
            throw new IllegalArgumentException("Ya existe otro tipo de servicio con el nombre: " + serviceTypeDetails.getName());
        }

        existingServiceType.setName(serviceTypeDetails.getName());
        return serviceTypeRepository.save(existingServiceType);
    }

    // ========== DELETE ==========
    @Transactional
    public void delete(Long id) {
        log.info("Deleting service type with id: {}", id);

        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de servicio no encontrado con id: " + id));

        serviceTypeRepository.delete(serviceType);
        log.info("Service type deleted successfully: {}", id);
    }

    // ========== VALIDATION ==========
    public boolean existsByName(String name) {
        return serviceTypeRepository.existsByName(name);
    }
}