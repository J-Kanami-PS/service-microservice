package org.example.cuidadodemascotas.servicemicroservice.apis.repository;

import org.example.cuidadodemascota.commons.entities.service.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, IBaseRepository<Service> {

    @Query("SELECT s FROM Service s WHERE s.active = true ORDER BY s.id DESC")
    Page<Service> findAllServices(Pageable pageable);

    @Query("SELECT s FROM Service s WHERE s.carer.id = :carerId AND s.active = true ORDER BY s.id DESC")
    Page<Service> findByCarerIdPaged(@Param("carerId") Long carerId, Pageable pageable);

    @Query("SELECT s FROM Service s WHERE s.serviceType.id = :serviceTypeId AND s.active = true ORDER BY s.id DESC")
    Page<Service> findByServiceTypeIdPaged(@Param("serviceTypeId") Long serviceTypeId, Pageable pageable);

    @Query("SELECT s FROM Service s WHERE " +
            "s.price >= :minPrice AND " +
            "s.price <= :maxPrice AND " +
            "s.active = true " +
            "ORDER BY s.price ASC")
    Page<Service> findByPriceRangePaged(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    @Query("SELECT s FROM Service s WHERE " +
            "s.carer.id = :carerId AND " +
            "s.serviceType.id = :serviceTypeId AND " +
            "s.active = true " +
            "ORDER BY s.id DESC")
    Page<Service> findByCarerIdAndServiceTypeIdPaged(
            @Param("carerId") Long carerId,
            @Param("serviceTypeId") Long serviceTypeId,
            Pageable pageable
    );

    @Query("SELECT s FROM Service s WHERE " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :text, '%')) AND " +
            "s.active = true")
    Page<Service> searchByDescription(@Param("text") String text, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Service s WHERE s.active = true")
    long countByActiveTrue();

    @Query("SELECT COUNT(s) FROM Service s WHERE s.carer.id = :carerId AND s.active = true")
    long countByCarerId(@Param("carerId") Long carerId);
}
