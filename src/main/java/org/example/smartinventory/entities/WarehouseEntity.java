package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.WarehouseType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="warehouses")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    private String warehouseName;

    private String warehouseAddress;

    private String emailAddress;

    private double totalCapacity;

    private double usedCapacity;

    @Enumerated(EnumType.STRING)
    private WarehouseType warehouseType;

    private String managerName;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private LocalDate establishmentDate;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<InventoryEntity> inventoryEntityCollections;

    @ManyToMany
    private Set<SupplierEntity> supplierEntityCollections;

    public WarehouseEntity(int warehouseId, String warehouseName, String warehouseAddress, String emailAddress, double totalCapacity, double usedCapacity, WarehouseType warehouseType, String managerName, LocalTime openingTime, LocalTime closingTime, LocalDate establishmentDate, Collection<InventoryEntity> inventoryEntityCollections, Set<SupplierEntity> supplierEntityCollections) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
        this.emailAddress = emailAddress;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = usedCapacity;
        this.warehouseType = warehouseType;
        this.managerName = managerName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.establishmentDate = establishmentDate;
        this.inventoryEntityCollections = inventoryEntityCollections;
        this.supplierEntityCollections = supplierEntityCollections;
    }
}
