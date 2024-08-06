package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    @ManyToMany
    private Set<SupplierEntity> suppliers = new HashSet<>();

    public WarehouseEntity(int warehouseId, String warehouseName, String warehouseAddress) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
    }
}
