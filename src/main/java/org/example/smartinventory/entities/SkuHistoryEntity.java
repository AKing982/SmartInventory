package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="sku_histories")
@Entity
@Data
@NoArgsConstructor
public class SkuHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="categoryCode")
    private String categoryCode;

    @Column(name="supplierCode")
    private String supplierCode;

    @Column(name="dateAdded")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateAdded;
}
