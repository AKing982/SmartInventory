package org.example.smartinventory.service;

import org.example.smartinventory.entities.InventoryEntity;
import org.example.smartinventory.entities.SupplierEntity;
import org.example.smartinventory.entities.WarehouseEntity;
import org.example.smartinventory.model.WarehouseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public interface WarehouseService extends ServiceModel<WarehouseEntity>
{
    // CRUD Operations
    WarehouseEntity createWarehouse(WarehouseEntity warehouse);
    Page<WarehouseEntity> getAllWarehousesPaginated(Pageable pageable);
    WarehouseEntity updateWarehouse(WarehouseEntity warehouse);
    void deleteWarehouse(int warehouseId);

    // Search and Filter
    List<WarehouseEntity> searchWarehouses(String keyword);
    List<WarehouseEntity> getWarehousesByType(WarehouseType type);
    List<WarehouseEntity> getWarehousesByCapacityRange(double minCapacity, double maxCapacity);
    List<WarehouseEntity> getWarehousesByUtilizationRate(double minRate, double maxRate);

    // Capacity Management
    double getTotalCapacity(int warehouseId);
    double getUsedCapacity(int warehouseId);
    double getAvailableCapacity(int warehouseId);
    WarehouseEntity updateCapacity(int warehouseId, double newTotalCapacity);
    WarehouseEntity updateUsedCapacity(int warehouseId, double newUsedCapacity);

    // Inventory Management
    List<InventoryEntity> getWarehouseInventory(int warehouseId);
    WarehouseEntity addInventoryItem(int warehouseId, InventoryEntity item);
    WarehouseEntity removeInventoryItem(int warehouseId, int inventoryItemId);
    WarehouseEntity updateInventoryItemQuantity(int warehouseId, int inventoryItemId, int newQuantity);

    // Supplier Management
    Set<SupplierEntity> getWarehouseSuppliers(int warehouseId);
    WarehouseEntity addSupplier(int warehouseId, SupplierEntity supplier);
    WarehouseEntity removeSupplier(int warehouseId, int supplierId);

    // Operating Hours Management
    WarehouseEntity updateOpeningTime(int warehouseId, LocalTime newOpeningTime);
    WarehouseEntity updateClosingTime(int warehouseId, LocalTime newClosingTime);
    boolean isWarehouseOpen(int warehouseId, LocalTime time);

    // Manager Operations
    WarehouseEntity assignManager(int warehouseId, String managerName);
    String getWarehouseManager(int warehouseId);

    // Analytics and Reporting
    double getWarehouseUtilizationRate(int warehouseId);
    List<WarehouseEntity> getMostUtilizedWarehouses(int limit);
    List<WarehouseEntity> getLeastUtilizedWarehouses(int limit);
    int getTotalInventoryCount(int warehouseId);
    double getTotalInventoryValue(int warehouseId);

    // Date-based Operations
    List<WarehouseEntity> getWarehousesEstablishedBefore(LocalDate date);
    List<WarehouseEntity> getWarehousesEstablishedAfter(LocalDate date);
    int getWarehouseAge(int warehouseId);

    // Bulk Operations
    List<WarehouseEntity> bulkCreateWarehouses(List<WarehouseEntity> warehouses);
    void bulkDeleteWarehouses(List<Integer> warehouseIds);
    List<WarehouseEntity> bulkUpdateWarehouseType(List<Integer> warehouseIds, WarehouseType newType);

    // Export and Import
    byte[] exportWarehousesToCsv();
    List<WarehouseEntity> importWarehousesFromCsv(byte[] csvData);

    // Statistics
    long getWarehouseCount();
    long getWarehouseCountByType(WarehouseType type);
    double getAverageWarehouseCapacity();

    // Advanced Queries
    List<WarehouseEntity> findNearestWarehouses(double latitude, double longitude, int limit);
    List<WarehouseEntity> findWarehousesWithProduct(int productId);
    List<WarehouseEntity> findWarehousesWithAvailableCapacity(double requiredCapacity);

    // Validation
    boolean isValidWarehouseAddress(String address);
    boolean isUniqueWarehouseName(String name);

    // Reporting
    byte[] generateWarehouseReport(int warehouseId);
    byte[] generateAllWarehousesReport();

    // Optimization Suggestions
    List<String> getOptimizationSuggestions(int warehouseId);

    // Alerts
    void sendCapacityAlert(int warehouseId, double threshold);

    // Custom Queries
    Page<WarehouseEntity> findWarehousesByCustomCriteria(String name, WarehouseType type,
                                                         Double minCapacity, Double maxCapacity,
                                                         LocalDate establishedAfter, Pageable pageable);
}
