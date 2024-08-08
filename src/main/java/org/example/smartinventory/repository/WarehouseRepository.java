package org.example.smartinventory.repository;

import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.model.WarehouseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long>
{

    @Query("SELECT w FROM WarehouseEntity w WHERE w.warehouseId IN :ids")
    List<WarehouseEntity> findAllById(@Param("ids") Iterable<Long> ids);

    // Custom queries (previously defined)
    @Query("SELECT w FROM WarehouseEntity w WHERE w.warehouseName = :name")
    Optional<WarehouseEntity> findByWarehouseName(@Param("name") String warehouseName);

    @Query("SELECT w FROM WarehouseEntity w WHERE w.warehouseAddress = :address")
    List<WarehouseEntity> findByWarehouseAddress(@Param("address") String warehouseAddress);

    @Query("SELECT w FROM WarehouseEntity w WHERE LOWER(w.warehouseName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(w.warehouseAddress) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<WarehouseEntity> searchWarehouses(@Param("keyword") String keyword);

    @Query("SELECT w FROM WarehouseEntity w WHERE w.warehouseType = :type")
    List<WarehouseEntity> getWarehousesByType(@Param("type") WarehouseType type);

    @Modifying
    @Query("UPDATE WarehouseEntity w SET " +
            "w.warehouseName = :#{#warehouse.warehouseName}, " +
            "w.warehouseAddress = :#{#warehouse.warehouseAddress}, " +
            "w.emailAddress = :#{#warehouse.emailAddress}, " +
            "w.totalCapacity = :#{#warehouse.totalCapacity}, " +
            "w.usedCapacity = :#{#warehouse.usedCapacity}, " +
            "w.warehouseType = :#{#warehouse.warehouseType}, " +
            "w.managerName = :#{#warehouse.managerName}, " +
            "w.openingTime = :#{#warehouse.openingTime}, " +
            "w.closingTime = :#{#warehouse.closingTime}, " +
            "w.establishmentDate = :#{#warehouse.establishmentDate} " +
            "WHERE w.warehouseId = :#{#warehouse.warehouseId}")
    int updateWarehouse(@Param("warehouse") WarehouseEntity warehouse);

    // DELETE
    @Modifying
    @Query("DELETE FROM WarehouseEntity w WHERE w.warehouseId = :id")
    void deleteById(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM WarehouseEntity w")
    void deleteAll();

    @Query("SELECT w FROM WarehouseEntity w WHERE w.totalCapacity BETWEEN :minCapacity AND :maxCapacity")
    List<WarehouseEntity> getWarehousesByCapacityRange(@Param("minCapacity") double minCapacity, @Param("maxCapacity") double maxCapacity);

    @Query("SELECT w FROM WarehouseEntity w WHERE (w.usedCapacity / w.totalCapacity) BETWEEN :minRate AND :maxRate")
    List<WarehouseEntity> getWarehousesByUtilizationRate(@Param("minRate") double minRate, @Param("maxRate") double maxRate);

    @Query("SELECT w.totalCapacity FROM WarehouseEntity w WHERE w.warehouseId = :id")
    double getTotalCapacity(@Param("id") int warehouseId);

    @Query("SELECT w.usedCapacity FROM WarehouseEntity w WHERE w.warehouseId = :id")
    double getUsedCapacity(@Param("id") int warehouseId);

    @Query("SELECT (w.totalCapacity - w.usedCapacity) FROM WarehouseEntity w WHERE w.warehouseId = :id")
    double getAvailableCapacity(@Param("id") int warehouseId);

    @Modifying
    @Query("UPDATE WarehouseEntity w SET w.totalCapacity = :newCapacity WHERE w.warehouseId = :id")
    void updateCapacity(@Param("id") int warehouseId, @Param("newCapacity") double newTotalCapacity);

    @Modifying
    @Query("UPDATE WarehouseEntity w SET w.usedCapacity = :newUsedCapacity WHERE w.warehouseId = :id")
    void updateUsedCapacity(@Param("id") int warehouseId, @Param("newUsedCapacity") double newUsedCapacity);

    @Query("SELECT w.managerName FROM WarehouseEntity w WHERE w.warehouseId = :id")
    String getWarehouseManager(@Param("id") int warehouseId);

    @Query("SELECT w FROM WarehouseEntity w ORDER BY (w.usedCapacity / w.totalCapacity) DESC")
    List<WarehouseEntity> getMostUtilizedWarehouses(Pageable pageable);

    @Query("SELECT w FROM WarehouseEntity w ORDER BY (w.usedCapacity / w.totalCapacity) ASC")
    List<WarehouseEntity> getLeastUtilizedWarehouses(Pageable pageable);

    @Query("SELECT w FROM WarehouseEntity w WHERE w.establishmentDate < :date")
    List<WarehouseEntity> getWarehousesEstablishedBefore(@Param("date") LocalDate date);

    @Query("SELECT w FROM WarehouseEntity w WHERE w.establishmentDate > :date")
    List<WarehouseEntity> getWarehousesEstablishedAfter(@Param("date") LocalDate date);

    @Query("SELECT CURRENT_DATE - w.establishmentDate FROM WarehouseEntity w WHERE w.warehouseId = :id")
    int getWarehouseAge(@Param("id") int warehouseId);

    @Query("SELECT COUNT(w) FROM WarehouseEntity w")
    long getWarehouseCount();

    @Query("SELECT COUNT(w) FROM WarehouseEntity w WHERE w.warehouseType = :type")
    long getWarehouseCountByType(@Param("type") WarehouseType type);

    @Query("SELECT AVG(w.totalCapacity) FROM WarehouseEntity w")
    double getAverageWarehouseCapacity();

    @Query("SELECT w FROM WarehouseEntity w WHERE w.totalCapacity - w.usedCapacity >= :requiredCapacity")
    List<WarehouseEntity> findWarehousesWithAvailableCapacity(@Param("requiredCapacity") double requiredCapacity);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN false ELSE true END FROM WarehouseEntity w WHERE w.warehouseName = :name")
    boolean isUniqueWarehouseName(@Param("name") String name);

    @Modifying
    @Query("UPDATE WarehouseEntity w SET w.managerName = :newManager WHERE w.warehouseId = :id")
    int updateWarehouseManager(@Param("id") int id, @Param("newManager") String newManager);

    @Query("SELECT w FROM WarehouseEntity w WHERE " +
            "(:name IS NULL OR LOWER(w.warehouseName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:type IS NULL OR w.warehouseType = :type) AND " +
            "(:minCapacity IS NULL OR w.totalCapacity >= :minCapacity) AND " +
            "(:maxCapacity IS NULL OR w.totalCapacity <= :maxCapacity) AND " +
            "(:establishedAfter IS NULL OR w.establishmentDate > :establishedAfter)")
    Page<WarehouseEntity> findWarehousesByCustomCriteria(
            @Param("name") String name,
            @Param("type") WarehouseType type,
            @Param("minCapacity") Double minCapacity,
            @Param("maxCapacity") Double maxCapacity,
            @Param("establishedAfter") LocalDate establishedAfter,
            Pageable pageable);

    @Modifying
    @Query("UPDATE WarehouseEntity w SET w.warehouseType = :newType WHERE w.warehouseId IN :ids")
    void bulkUpdateWarehouseType(@Param("ids") List<Integer> warehouseIds, @Param("newType") WarehouseType newType);
}
