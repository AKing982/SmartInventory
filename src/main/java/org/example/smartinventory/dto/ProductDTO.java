package org.example.smartinventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDTO(String productName, String productSKU,
                         int productQuantity, BigDecimal productPrice,
                         String productCategory, String productDescription,
                         String productBrand, BigDecimal costPrice,
                         LocalDate expirationDate, String notes,
                         int reorderPoint, String supplier, String modelNumber,
                         double markupPercentage) {
}
