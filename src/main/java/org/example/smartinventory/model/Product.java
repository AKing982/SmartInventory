package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Product {

    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int productQuantity;
    private String productCategory;
    private String productSKU;

    public Product(int productId, String productName, String productDescription, BigDecimal productPrice, int productQuantity, String productCategory, String productSKU) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.productSKU = productSKU;
    }
}
