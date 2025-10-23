package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.credential.User;
import org.example.cuidadodemascota.commons.entities.enums.AvailabilityStateEnum;
import org.example.cuidadodemascota.commons.entities.service.Service;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascota.commons.entities.user.Carer;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.CarerWithServicesRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.CarerWithServicesResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.UserResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ICarerRepository;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceRepository;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.ServiceTypeRepository;
import org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException;
import org.example.cuidadodemascotas.servicemicroservice.utils.ServiceMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarerService {

    private final ICarerRepository carerRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceMapper serviceMapper;

    /**
     * CREAR CUIDADOR CON SERVICIOS (CABECERA-DETALLE)
     * Un solo endpoint para guardar todo
     */
    @Transactional
    public CarerWithServicesResponseDTO createCarerWithServices(CarerWithServicesRequestDTO dto) {
        log.info("Creating carer with services for userId: {}", dto.getUserId());

        // 1. Validar que el usuario existe
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("El ID de usuario es requerido");
        }

        // 2. Crear el Carer (CABECERA)
        Carer carer = new Carer();

        User user = new User();
        user.setId(dto.getUserId());
        carer.setUser(user);

        carer.setState(AvailabilityStateEnum.valueOf(dto.getAvailabilityState().name()));
        carer.setAmount_pet(dto.getAmountPet().shortValue());
        carer.setActive(true);

        Carer savedCarer = carerRepository.save(carer);
        log.info("Carer created with id: {}", savedCarer.getId());

        // 3. Crear los servicios (DETALLE) si se proporcionaron
        List<ServiceResponseDTO> servicesResponse = new ArrayList<>();

        if (dto.getServices() != null && !dto.getServices().isEmpty()) {
            for (var serviceDto : dto.getServices()) {
                // Validar que el tipo de servicio existe
                ServiceType serviceType = serviceTypeRepository.findById(serviceDto.getServiceTypeId())
                        .orElseThrow(() -> new NotFoundException(serviceDto.getServiceTypeId(), ServiceType.class));

                Service service = new Service();
                service.setCarer(savedCarer);
                service.setServiceType(serviceType);
                service.setDescription(serviceDto.getDescription());
                service.setPrice(BigDecimal.valueOf(serviceDto.getPrice()));
                service.setActive(true);

                Service savedService = serviceRepository.save(service);
                servicesResponse.add(serviceMapper.toDto(savedService));

                log.debug("Service created with id: {} for carer: {}", savedService.getId(), savedCarer.getId());
            }
        }

        log.info("Carer created with {} services", servicesResponse.size());

        // 4. Construir respuesta
        return buildCarerWithServicesResponse(savedCarer, servicesResponse);
    }

    /**
     * OBTENER CUIDADOR CON SUS SERVICIOS
     */
    @Transactional(readOnly = true)
    public CarerWithServicesResponseDTO getCarerWithServices(Long carerId) {
        log.debug("Getting carer with services, carerId: {}", carerId);

        Carer carer = carerRepository.findByIdAndActiveTrue(carerId)
                .orElseThrow(() -> new NotFoundException(carerId, Carer.class));

        // Obtener todos los servicios del cuidador
        List<Service> services = serviceRepository.findByCarerIdAndActiveTrue(carerId);
        List<ServiceResponseDTO> servicesDto = services.stream()
                .map(serviceMapper::toDto)
                .toList();

        return buildCarerWithServicesResponse(carer, servicesDto);
    }

    /**
     * ACTUALIZAR CUIDADOR CON SERVICIOS (CABECERA-DETALLE)
     * Una sola llamada para actualizar todo
     */
    @Transactional
    public CarerWithServicesResponseDTO updateCarerWithServices(Long carerId, CarerWithServicesRequestDTO dto) {
        log.info("Updating carer with services, carerId: {}", carerId);

        // 1. Buscar el cuidador existente
        Carer carer = carerRepository.findByIdAndActiveTrue(carerId)
                .orElseThrow(() -> new NotFoundException(carerId, Carer.class));

        // 2. Actualizar datos del cuidador (CABECERA)
        carer.setState(AvailabilityStateEnum.valueOf(dto.getAvailabilityState().name()));
        carer.setAmount_pet(dto.getAmountPet().shortValue());

        Carer updatedCarer = carerRepository.save(carer);
        log.info("Carer updated: {}", carerId);

        // 3. Actualizar servicios (DETALLE)
        List<ServiceResponseDTO> servicesResponse = new ArrayList<>();

        if (dto.getServices() != null && !dto.getServices().isEmpty()) {
            // Estrategia: Desactivar todos los servicios existentes y crear nuevos
            // (Otra opción sería comparar y actualizar, pero es más complejo)

            List<Service> existingServices = serviceRepository.findByCarerIdAndActiveTrue(carerId);
            for (Service existingService : existingServices) {
                existingService.setActive(false);
                serviceRepository.save(existingService);
            }
            log.info("Deactivated {} existing services", existingServices.size());

            // Crear los nuevos servicios
            for (var serviceDto : dto.getServices()) {
                ServiceType serviceType = serviceTypeRepository.findById(serviceDto.getServiceTypeId())
                        .orElseThrow(() -> new NotFoundException(serviceDto.getServiceTypeId(), ServiceType.class));

                Service service = new Service();
                service.setCarer(updatedCarer);
                service.setServiceType(serviceType);
                service.setDescription(serviceDto.getDescription());
                service.setPrice(BigDecimal.valueOf(serviceDto.getPrice()));
                service.setActive(true);

                Service savedService = serviceRepository.save(service);
                servicesResponse.add(serviceMapper.toDto(savedService));
            }

            log.info("Created {} new services", servicesResponse.size());
        }

        return buildCarerWithServicesResponse(updatedCarer, servicesResponse);
    }

    /**
     * Helper para construir el DTO de respuesta
     */
    private CarerWithServicesResponseDTO buildCarerWithServicesResponse(Carer carer, List<ServiceResponseDTO> services) {
        CarerWithServicesResponseDTO response = new CarerWithServicesResponseDTO();
        response.setId(carer.getId());

        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setId(carer.getUser().getId());
        userDto.setName(carer.getUser().getName());
        userDto.setLastName(carer.getUser().getLastName());
        userDto.setEmail(carer.getUser().getEmail());
        userDto.setPhoneNumber(carer.getUser().getPhoneNumber());
        userDto.setProfilePhoto(carer.getUser().getProfilePhoto());
        userDto.setCreatedAt(carer.getUser().getCreatedAt() != null
                ? carer.getUser().getCreatedAt().atOffset(java.time.ZoneOffset.UTC)
                : null);
        userDto.setUpdatedAt(carer.getUser().getUpdatedAt() != null
                ? carer.getUser().getUpdatedAt().atOffset(java.time.ZoneOffset.UTC)
                : null);
        userDto.setActive(carer.getUser().getActive());

        response.setUser(userDto);
        response.setAvailabilityState(CarerWithServicesResponseDTO.AvailabilityStateEnum.valueOf(carer.getState().name()));
        response.setAmountPet(carer.getAmount_pet().intValue());
        response.setCreatedAt(carer.getCreatedAt() != null ? carer.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null);
        response.setUpdatedAt(carer.getUpdatedAt() != null ? carer.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC) : null);
        response.setActive(carer.getActive());
        response.setServices(services);

        return response;
    }
}