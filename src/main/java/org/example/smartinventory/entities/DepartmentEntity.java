package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="departments")
@Data
@NoArgsConstructor
public class DepartmentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(nullable = false, unique = true)
    private String departmentName;

    @Column(nullable = false)
    private String departmentDescription;

    @Column(name="manager_id")
    private int managerId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;

    private String address;

    public DepartmentEntity(int departmentId, String departmentName, String departmentDescription, int managerId, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive, List<EmployeeEntity> employees, String address) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.managerId = managerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.employees = employees;
        this.address = address;
    }

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }
}
