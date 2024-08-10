package org.example.smartinventory.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
        property = "productId"
)
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotBlank(message="Product Name cannot be null")
    private String productName;

    @NotBlank(message="Product description cannot be blank")
    private String productDescription;

    @NotBlank(message="Product SKU cannot be blank")
    private String productSKU;

    @Size(min=1)
    private BigDecimal productPrice;

    private int productQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoryId")
    @JsonBackReference
    private CategoryEntity productCategory;

    @JsonIgnore
    @Column(name="dateAdded")
    private LocalDate dateAdded;


}
