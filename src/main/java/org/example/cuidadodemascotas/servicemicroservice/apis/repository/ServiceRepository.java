package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.service.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    // ==== FIND BY ID ====
    Optional<Service> findByIdAndActiveTrue(Long id);

    // ==== FIND BY CARER ====
    List<Service> findByCarerIdAndActiveTrue(Long carerId);

    // ==== FIND BY SERVICE TYPE ====
    List<Service> findByServiceTypeIdAndActiveTrue(Long serviceTypeId);

    // ==== FIND BY CARER AND SERVICE TYPE ====
    List<Service> findByCarerIdAndServiceTypeIdAndActiveTrue(Long carerId, Long serviceTypeId);

    // ==== PRICE RANGE QUERIES ====
    List<Service> findByPriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);

    List<Service> findByCarerIdAndPriceBetweenAndActiveTrue(Long carerId, BigDecimal minPrice, BigDecimal maxPrice);

    // ==== SEARCH BY DESCRIPTION ====
    @Query("SELECT s FROM Service s WHERE LOWER(s.description) LIKE LOWER(CONCAT('%', :description, '%')) AND s.active = true")
    List<Service> findByDescriptionContainingIgnoreCaseAndActiveTrue(@Param("description") String description);

    Page<Service> findByActiveTrue(Pageable pageable);

    @Query("SELECT s FROM Service s WHERE " +
            "(:carerId IS NULL OR s.carer.id = :carerId) AND " +
            "(:serviceTypeId IS NULL OR s.serviceType.id = :serviceTypeId) AND " +
            "(:minPrice IS NULL OR s.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR s.price <= :maxPrice) AND " +
            "s.active = true")
    Page<Service> findByFilters(
            @Param("carerId") Long carerId,
            @Param("serviceTypeId") Long serviceTypeId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    // ==== COUNT ACTIVE SERVICES ====
    long countByActiveTrue();

    long countByCarerIdAndActiveTrue(Long carerId);

    // ==== FIND ALL ACTIVE ====
    List<Service> findByActiveTrue();
}