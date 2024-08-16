package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="departments")
@Data
@NoArgsConstructor
public class DepartmentEntity
{
    @Id
    @Column(name="departmentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(nullable = false, unique = true, name="department_name")
    private String departmentName;

    @Column(nullable = false, name="department_description")
    private String departmentDescription;

    @Column(name="manager_id")
    private int managerId;

    @Column(name="createdat", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updatedat")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name="is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;

    @Column(name="deptAddress")
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
