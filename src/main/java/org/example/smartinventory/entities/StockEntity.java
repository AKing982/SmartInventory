package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name="stocks")
@Entity
@Data
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="isLow")
    private Boolean isLow;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "stock_product",
            joinColumns = @JoinColumn(name = "stockid"),
            inverseJoinColumns = @JoinColumn(name = "productid"))
    private Set<ProductEntity> products;

    @Column(name="last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdated;

}
