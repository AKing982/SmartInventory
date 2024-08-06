package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Supplier {

    private int supplierId;
    private String supplierName;
    private Address supplierAddress;
    private Contact supplierContact;
    private List<Product> supplierProducts;

    public Supplier(int supplierId, String supplierName, Address supplierAddress, Contact supplierContact, List<Product> supplierProducts)
    {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierContact = supplierContact;
        this.supplierProducts = supplierProducts;
    }
}
