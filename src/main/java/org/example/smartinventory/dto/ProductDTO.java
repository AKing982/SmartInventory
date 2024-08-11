package org.example.smartinventory.dto;

import java.math.BigDecimal;

public record ProductDTO(String productName, String productSKU,
                         int productQuantity, BigDecimal productPrice,
                         String productCategory, String productDescription) {
}
