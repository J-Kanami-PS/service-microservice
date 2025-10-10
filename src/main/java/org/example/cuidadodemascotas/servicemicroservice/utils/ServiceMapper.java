package org.example.cuidadodemascotas.servicemicroservice.utils;

import org.example.cuidadodemascota.commons.entities.service.Service;
import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascota.commons.entities.user.Carer;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class ServiceMapper extends GenericMapper<Service, ServiceResponseDTO> {

    public ServiceMapper() {
        super(Service.class, ServiceResponseDTO.class);
    }

    @Override
    public ServiceResponseDTO toDto(Service entity) {
        if (entity == null) {
            return null;
        }

        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setId(entity.getId());
        dto.setCarerId(entity.getCarer() != null ? entity.getCarer().getId() : null);
        dto.setServiceTypeId(entity.getServiceType() != null ? entity.getServiceType().getId() : null);
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice() != null ? entity.getPrice().doubleValue() : null);
        dto.setCreatedAt(toOffsetDateTime(entity.getCreatedAt()));
        dto.setUpdatedAt(toOffsetDateTime(entity.getUpdatedAt()));
        dto.setActive(entity.getActive());

        return dto;
    }

    public Service toEntity(ServiceRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Service entity = new Service();

        // Configurar Carer
        if (dto.getCarerId() != null) {
            Carer carer = new Carer();
            carer.setId(dto.getCarerId());
            entity.setCarer(carer);
        }

        // Configurar ServiceType
        if (dto.getServiceTypeId() != null) {
            ServiceType serviceType = new ServiceType();
            serviceType.setId(dto.getServiceTypeId());
            entity.setServiceType(serviceType);
        }

        entity.setDescription(dto.getDescription());

        if (dto.getPrice() != null) {
            entity.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }

        return entity;
    }

    public void updateEntityFromDto(ServiceRequestDTO dto, Service entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getCarerId() != null) {
            Carer carer = new Carer();
            carer.setId(dto.getCarerId());
            entity.setCarer(carer);
        }

        if (dto.getServiceTypeId() != null) {
            ServiceType serviceType = new ServiceType();
            serviceType.setId(dto.getServiceTypeId());
            entity.setServiceType(serviceType);
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getPrice() != null) {
            entity.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }
    }

    private OffsetDateTime toOffsetDateTime(java.time.LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
}