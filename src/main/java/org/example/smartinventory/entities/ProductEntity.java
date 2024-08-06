package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="products")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private String productDescription;

    private String productSKU;

    private BigDecimal productPrice;

    private int productQuantity;

    private String productCategory;

    private LocalDateTime dateAdded;

    public ProductEntity(int productId, String productName, String productDescription, String productSKU, BigDecimal productPrice, int productQuantity, String productCategory, LocalDateTime dateAdded) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productSKU = productSKU;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.dateAdded = dateAdded;
    }
}
