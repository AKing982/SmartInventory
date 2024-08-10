package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="managers")
@Entity
@Data
@NoArgsConstructor
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int managerId;

    private String managerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employeeId")
    private EmployeeEntity employee;

    @OneToOne
    @JoinColumn(name="departmentId")
    private DepartmentEntity department;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ManagerEntity(int managerId, EmployeeEntity employee, DepartmentEntity department, LocalDate startDate, LocalDate endDate, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.managerId = managerId;
        this.employee = employee;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
