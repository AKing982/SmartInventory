package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRoleAssignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="roleId", nullable = false)
    private EmployeeRolesEntity role;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @ManyToOne
    @JoinColumn(name="assignedBy", nullable = false)
    private EmployeeEntity employee;

    @Column(nullable = false)
    private boolean isActive;

    @PrePersist
    protected void onCreate()
    {
        assignedAt = LocalDateTime.now();
    }

}
