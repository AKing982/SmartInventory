package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.EmployeeRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name="employees")
@Builder
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String jobTitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="managerId")
    private ManagerEntity managerEntity;

    private String salary;

    private String username;

    private String passwordHash;

    private EmployeeRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="departmentId")
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouseId")
    private WarehouseEntity warehouse;

    @Column(nullable = false)
    private LocalDate hireDate;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name="EmployeeRoleAssignments",
                    joinColumns = @JoinColumn(name="employeeId"))
    private Set<EmployeeRoleAssignment> roleAssignments;


    public EmployeeEntity(int employeeId, String firstName, String lastName, String email, String phone, String jobTitle, ManagerEntity managerEntity, String salary, String username, String passwordHash, EmployeeRole role, DepartmentEntity department, WarehouseEntity warehouse, LocalDate hireDate, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, Set<EmployeeRoleAssignment> roleAssignments) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.managerEntity = managerEntity;
        this.salary = salary;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.department = department;
        this.warehouse = warehouse;
        this.hireDate = hireDate;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roleAssignments = roleAssignments;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isActive = true;
    }

}
