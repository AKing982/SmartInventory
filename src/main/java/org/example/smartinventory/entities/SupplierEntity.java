package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.EmployeeRole;

@Entity
@Table(name="suppliers")
@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC)
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", unique = true)
    private UserEntity user;

    private String supplierName;

    private EmployeeRole employeeRole;
}
