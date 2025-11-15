package org.example.cuidadodemascotas.servicemicroservice.utils;

import org.example.cuidadodemascota.commons.entities.service.ServiceType;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceTypeResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper extends GenericMapper<ServiceType, ServiceTypeResponseDTO> {

    @Autowired
    public ServiceTypeMapper(ModelMapper modelMapper) {
        super(modelMapper, ServiceType.class, ServiceTypeResponseDTO.class);
    }

    @Override
    public ServiceTypeResponseDTO toDto(ServiceType entity) {
        if (entity == null) {
            return null;
        }
        ServiceTypeResponseDTO dto = new ServiceTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
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