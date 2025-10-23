package org.example.cuidadodemascotas.servicemicroservice.apis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.CarerWithServicesRequestDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.CarerWithServicesResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.service.CarerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarerController implements CarerApi {

    private final CarerService carerService;

    /**
     * CREAR CUIDADOR CON SERVICIOS (CABECERA-DETALLE)
     * POST /carers-with-services
     *
     * Cumple criterio: "Guardar nuevos objetos mucho a mucho
     * (un solo endpoint para guardar cabecera-detalle)"
     */
    @Override
    public ResponseEntity<CarerWithServicesResponseDTO> createCarerWithServices(
            @Valid CarerWithServicesRequestDTO dto) {

        log.info("POST /carers-with-services - Creating carer with {} services",
                dto.getServices() != null ? dto.getServices().size() : 0);

        CarerWithServicesResponseDTO response = carerService.createCarerWithServices(dto);

        log.info("Carer created with id: {} and {} services",
                response.getId(), response.getServices().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * OBTENER CUIDADOR CON SERVICIOS
     * GET /carers-with-services/{carerId}
     *
     * Cumple criterio: "Obtener objeto relación mucho a mucho por id"
     */
    @Override
    public ResponseEntity<CarerWithServicesResponseDTO> getCarerWithServices(Long carerId) {
        log.info("GET /carers-with-services/{} - Getting carer with services", carerId);

        CarerWithServicesResponseDTO response = carerService.getCarerWithServices(carerId);

        return ResponseEntity.ok(response);
    }

    /**
     * ACTUALIZAR CUIDADOR CON SERVICIOS (CABECERA-DETALLE)
     * PUT /carers-with-services/{carerId}
     *
     * Cumple criterio: "Modificar objeto con relación mucho a mucho
     * (una sola llamada para actualiza cabecera-detalle)"
     */
    @Override
    public ResponseEntity<CarerWithServicesResponseDTO> updateCarerWithServices(
            Long carerId,
            @Valid CarerWithServicesRequestDTO dto) {

        log.info("PUT /carers-with-services/{} - Updating carer with {} services",
                carerId, dto.getServices() != null ? dto.getServices().size() : 0);

        CarerWithServicesResponseDTO response = carerService.updateCarerWithServices(carerId, dto);

        log.info("Carer updated: {} with {} services",
                carerId, response.getServices().size());

        return ResponseEntity.ok(response);
    }
}