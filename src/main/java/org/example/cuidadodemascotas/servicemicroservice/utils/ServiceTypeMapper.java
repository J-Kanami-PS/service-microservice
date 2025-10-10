package org.example.cuidadodemascotas.servicemicroservice.utils;

import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper extends GenericMapper<ServiceType, ServiceTypeResponseDTO> {

    public ServiceTypeMapper() {
        super(ServiceType.class, ServiceTypeResponseDTO.class);
    }

    public ServiceType toEntity(ServiceTypeRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        ServiceType entity = new ServiceType();
        entity.setName(dto.getName());
        return entity;
    }

    public void updateEntityFromDto(ServiceTypeRequestDTO dto, ServiceType entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
    }
}