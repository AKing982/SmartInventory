package org.example.smartinventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int productQuantity;
    private String productCategory;
    private String productSKU;
    private String productBrand;
    private BigDecimal costPrice;
    private LocalDate expirationDate;
    private String notes;
    private int reorderPoint;
    private String supplier;
    private String modelNumber;
    private double markupPercentage;

}
