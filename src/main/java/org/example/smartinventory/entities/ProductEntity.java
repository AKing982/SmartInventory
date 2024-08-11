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

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="categoryId")
//    @JsonBackReference
//    private CategoryEntity productCategory;

    @JsonIgnore
    @Column(name="dateAdded")
    private LocalDate dateAdded;

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
