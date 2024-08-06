package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Inventory {

    private int id;
    private Product product;
    private Warehouse warehouse;
    private int quantity;
    private LocalDate lastUpdated;

    public Inventory(int id, Product product, Warehouse warehouse, int quantity, LocalDate lastUpdated) {
        this.id = id;
        this.product = product;
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }
}
