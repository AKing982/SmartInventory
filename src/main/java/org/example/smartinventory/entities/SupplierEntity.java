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
    @Column(name="supplier_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userid", unique = true)
    private UserEntity user;

    @Column(name="supplier_name")
    private String supplierName;

    @Enumerated(EnumType.STRING)
    @Column(name="employee_role")
    private EmployeeRole employeeRole;
}
