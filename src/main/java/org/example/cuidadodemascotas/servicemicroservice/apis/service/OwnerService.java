package org.example.cuidadodemascotas.servicemicroservice.apis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascota.commons.entities.user.Owner;
import org.example.cuidadodemascota.commons.entities.credential.User;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.OwnerResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.UserResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.repository.IOwnerRepository;
import org.example.cuidadodemascotas.servicemicroservice.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final IOwnerRepository ownerRepository;

    /**
     * OBTENER TODOS LOS PROPIETARIOS ACTIVOS
     */
    @Transactional(readOnly = true)
    public List<OwnerResponseDTO> getAllOwners() {
        log.info("Getting all active owners");

        List<Owner> owners = ownerRepository.findByActiveTrue();
        List<OwnerResponseDTO> result = new ArrayList<>();

        for (Owner owner : owners) {
            result.add(buildOwnerResponse(owner));
        }

        log.info("Found {} active owners", result.size());
        return result;
    }

    /**
     * OBTENER UN PROPIETARIO POR ID
     */
    @Transactional(readOnly = true)
    public OwnerResponseDTO getOwnerById(Long ownerId) {
        log.info("Getting owner by id: {}", ownerId);

        Owner owner = ownerRepository.findByIdAndActiveTrue(ownerId)
                .orElseThrow(() -> new NotFoundException(ownerId, Owner.class));

        return buildOwnerResponse(owner);
    }

    /**
     * Helper para mapear Owner -> OwnerResponseDTO
     */
    private OwnerResponseDTO buildOwnerResponse(Owner owner) {
        OwnerResponseDTO dto = new OwnerResponseDTO();
        dto.setId(owner.getId());
        dto.setActive(owner.getActive());
        dto.setCreatedAt(owner.getCreatedAt() != null ? owner.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null);
        dto.setUpdatedAt(owner.getUpdatedAt() != null ? owner.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC) : null);

        User user = owner.getUser();
        if (user != null) {
            UserResponseDTO userDto = new UserResponseDTO();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setProfilePhoto(user.getProfilePhoto());
            userDto.setActive(user.getActive());
            userDto.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null);
            userDto.setUpdatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC) : null);
            dto.setUser(userDto);
        }

        return dto;
    }
}
