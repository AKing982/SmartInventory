package org.example.smartinventory.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Data
@NoArgsConstructor
@Builder
public class Warehouse
{
    private int warehouseId;
    private String warehouseName;
    private Address warehouseAddress;
    private String emailAddress;
    private double totalCapacity;
    private double usedCapacity;
    private WarehouseType warehouseType;
    private String warehouseManager;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalDate establishmentDate;
    private Collection<Inventory> inventory;
    private Set<Supplier> suppliers = new HashSet<>();

    public Warehouse(int warehouseId, String warehouseName, Address warehouseAddress, String emailAddress, double totalCapacity, double usedCapacity, WarehouseType warehouseType, String warehouseManager, LocalTime openingTime, LocalTime closingTime, LocalDate establishmentDate, Collection<Inventory> inventory, Set<Supplier> suppliers) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
        this.emailAddress = emailAddress;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = usedCapacity;
        this.warehouseType = warehouseType;
        this.warehouseManager = warehouseManager;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.establishmentDate = establishmentDate;
        this.inventory = inventory;
        this.suppliers = suppliers;
    }
}
