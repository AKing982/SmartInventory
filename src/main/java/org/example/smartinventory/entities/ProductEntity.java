package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank(message="Product Name cannot be blank")
    private String productName;

    @NotBlank(message="Product description cannot be blank")
    private String productDescription;

    @NotBlank(message="Product SKU cannot be blank")
    private String productSKU;

    @NotNull
    @Size(min=1)
    private BigDecimal productPrice;

    @Size(min=1)
    private int productQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private CategoryEntity productCategory;

    private LocalDateTime dateAdded;

    public ProductEntity(int productId, String productName, String productDescription, String productSKU, BigDecimal productPrice, int productQuantity, CategoryEntity productCategory, LocalDateTime dateAdded) {
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
