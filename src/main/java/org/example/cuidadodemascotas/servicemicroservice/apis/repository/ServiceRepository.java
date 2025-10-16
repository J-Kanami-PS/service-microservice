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

    /**
     * Obtener todos los servicios activos con paginación
     * GET /services?page=0&size=10
     */
    @Query("SELECT s FROM Service s WHERE s.active = true ORDER BY s.id DESC")
    Page<Service> findAllServices(Pageable pageable);

    /**
     * Obtener servicios filtrados por cuidador con paginación
     * GET /services/carer/{carerId}?page=0&size=10
     */
    @Query("SELECT s FROM Service s WHERE s.carer.id = :carerId AND s.active = true ORDER BY s.id DESC")
    Page<Service> findByCarerIdPaged(
            @Param("carerId") Long carerId,
            Pageable pageable
    );

    /**
     * Obtener servicios filtrados por tipo de servicio con paginación
     * GET /services/type/{serviceTypeId}?page=0&size=10
     */
    @Query("SELECT s FROM Service s WHERE s.serviceType.id = :serviceTypeId AND s.active = true ORDER BY s.id DESC")
    Page<Service> findByServiceTypeIdPaged(
            @Param("serviceTypeId") Long serviceTypeId,
            Pageable pageable
    );

    /**
     * Obtener servicios dentro de un rango de precio con paginación
     * GET /services/price-range/{minPrice}/{maxPrice}?page=0&size=10
     */
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

    /**
     * Obtener servicios filtrados por cuidador y tipo de servicio con paginación
     * GET /services/carer/{carerId}/type/{serviceTypeId}?page=0&size=10
     */
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

    /**
     * Obtener servicios de un cuidador en un rango de precio
     * GET /services/carer/{carerId}/price/{minPrice}/{maxPrice}?page=0&size=10
     */
    @Query("SELECT s FROM Service s WHERE " +
            "s.carer.id = :carerId AND " +
            "s.price >= :minPrice AND " +
            "s.price <= :maxPrice AND " +
            "s.active = true " +
            "ORDER BY s.price ASC")
    Page<Service> findByCarerIdAndPriceRangePaged(
            @Param("carerId") Long carerId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    /**
     * Obtener servicios de un tipo en un rango de precio
     * GET /services/type/{serviceTypeId}/price/{minPrice}/{maxPrice}?page=0&size=10
     */
    @Query("SELECT s FROM Service s WHERE " +
            "s.serviceType.id = :serviceTypeId AND " +
            "s.price >= :minPrice AND " +
            "s.price <= :maxPrice AND " +
            "s.active = true " +
            "ORDER BY s.price ASC")
    Page<Service> findByServiceTypeIdAndPriceRangePaged(
            @Param("serviceTypeId") Long serviceTypeId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    /**
     * Verificar si existe servicio activo por cuidador y tipo
     */
    @Query("SELECT COUNT(s) > 0 FROM Service s WHERE " +
            "s.carer.id = :carerId AND " +
            "s.serviceType.id = :serviceTypeId AND " +
            "s.active = true")
    boolean existsByCarerAndServiceType(
            @Param("carerId") Long carerId,
            @Param("serviceTypeId") Long serviceTypeId
    );

    /**
     * Contar servicios de un cuidador
     */
    @Query("SELECT COUNT(s) FROM Service s WHERE s.carer.id = :carerId AND s.active = true")
    long countByCarerId(@Param("carerId") Long carerId);

    /**
     * Contar servicios de un tipo
     */
    @Query("SELECT COUNT(s) FROM Service s WHERE s.serviceType.id = :serviceTypeId AND s.active = true")
    long countByServiceTypeId(@Param("serviceTypeId") Long serviceTypeId);

    /**
     * Contar servicios en rango de precio
     */
    @Query("SELECT COUNT(s) FROM Service s WHERE " +
            "s.price >= :minPrice AND " +
            "s.price <= :maxPrice AND " +
            "s.active = true")
    long countByPriceRange(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
}