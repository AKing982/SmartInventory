package org.example.smartinventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.EmployeeRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name="managers")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="employeeId", unique = true, nullable = false)
    private EmployeeEntity employee;

    @OneToOne
    @JoinColumn(name="departmentId")
    private DepartmentEntity department;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(name="is_active")
    private boolean is_active = true;

    @Column(nullable=false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="manager")
    private Set<EmployeeEntity> managedEmployees = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addManagedEmployee(EmployeeEntity employee) {
        managedEmployees.add(employee);
        employee.setManager(this);
    }

    public void removeManagedEmployee(EmployeeEntity employee) {
        managedEmployees.remove(employee);
        employee.setManager(null);
    }
}
