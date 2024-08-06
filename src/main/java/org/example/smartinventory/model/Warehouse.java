package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Warehouse
{
    private int warehouseId;
    private String warehouseName;
    private Address warehouseAddress;
    private List<Supplier> supplierList = new ArrayList<>();
}
