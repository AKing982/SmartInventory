package org.example.smartinventory.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="products")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(        // Add this line
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="sku")
    private String sku;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="quantity")
    private int quantity;

    @Column(name="category")
    private String category;

    @Column(name="productBrand")
    private String productBrand;

    @Column(name="costPrice")
    private BigDecimal costPrice;

    @Column(name="reorderPoint")
    private int reorderPoint;

    @Column(name="supplier")
    private String supplier;

    @Column(name="modelNumber")
    private String modelNumber;

    @Column(name="expirationDate")
    private LocalDate expirationDate;

    @Column(name="notes")
    private String notes;

    @Column(name="markupPercent")
    private double markupPercentage;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="categoryId")
//    @JsonBackReference
//    private CategoryEntity productCategory;

    @JsonIgnore
    @Column(name="dateadded")
    private LocalDate dateAdded;

    public ProductEntity(int id, String name, String description, String sku, BigDecimal price, int quantity, String category, LocalDate dateAdded) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", costPrice=" + costPrice +
                ", reorderPoint=" + reorderPoint +
                ", supplier='" + supplier + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", markupPercentage=" + markupPercentage +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
