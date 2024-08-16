package org.example.smartinventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.smartinventory.model.EmployeeRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name="employees")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userid", unique = true)
    private UserEntity user;

    @Column(name="hireDate", nullable = false)
    private LocalDate hireDate;

    @Column(name="jobTitle")
    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="departmentid")
    private DepartmentEntity department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="managerid")
    private ManagerEntity manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouseId")
    private WarehouseEntity warehouse;

    @Column(name="emprole")
    private EmployeeRole role;

    @Column(name="salary")
    private BigDecimal salary;

    @Column(nullable=false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
