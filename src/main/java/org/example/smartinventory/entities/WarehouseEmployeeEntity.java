package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name="warehouseEmployees")
@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WarehouseEmployeeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseEmployeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehouseEntity warehouse;

    @Column(name = "employee_name", nullable = false, length = 100)
    private String employeeName;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    // You might want to add more fields such as:
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    // Constructor without id (useful for creating new entities)

    public WarehouseEmployeeEntity(int warehouseEmployeeId, WarehouseEntity warehouse, String employeeName, String position, LocalDate hireDate, String email, String phoneNumber, EmploymentStatus status) {
        this.warehouseEmployeeId = warehouseEmployeeId;
        this.warehouse = warehouse;
        this.employeeName = employeeName;
        this.position = position;
        this.hireDate = hireDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}

enum EmploymentStatus {
    ACTIVE,
    ON_LEAVE,
    TERMINATED
}
