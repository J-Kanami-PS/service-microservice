package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.OwnerResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * GET /owners
     * Retorna todos los propietarios activos.
     */
    @GetMapping
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        log.info("GET /owners - Fetching all active owners");
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    /**
     * GET /owners/{id}
     * Retorna un propietario por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerById(@PathVariable Long id) {
        log.info("GET /owners/{} - Fetching owner by id", id);
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }
}
