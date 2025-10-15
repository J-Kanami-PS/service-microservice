package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.service.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends IBaseRepository<Service>,
        JpaSpecificationExecutor<Service> {

    @Override
    @Query("SELECT s FROM Service s WHERE s.id = :id AND s.active = true")
    Optional<Service> findByIdAndActiveTrue(@Param("id") Long id);

    List<Service> findByCarerIdAndActiveTrue(Long carerId);

    List<Service> findByServiceTypeIdAndActiveTrue(Long serviceTypeId);

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

    @Query("SELECT s FROM Service s WHERE " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :text, '%')) AND " +
            "s.active = true")
    Page<Service> searchByDescription(@Param("text") String text, Pageable pageable);

    long countByActiveTrue();

    long countByCarerIdAndActiveTrue(Long carerId);
}