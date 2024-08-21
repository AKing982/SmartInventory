package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="sku_histories")
@Entity
@Data
@NoArgsConstructor
public class SkuHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid")
    private UserEntity user;

    @Column(name="sequence")
    private int sequence;

    @Column(name="sku")
    private String sku;

    @Column(name="supplier")
    private String supplier;

}
