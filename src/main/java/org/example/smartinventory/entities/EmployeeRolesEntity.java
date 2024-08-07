package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.smartinventory.model.Permission;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="employeeRoles")
public class EmployeeRolesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false, unique = true, length = 50)
    private String roleName;

    @Column(columnDefinition = "TEXT")
    private String roleDescription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "role")
    private Set<EmployeeRoleAssignment> roleAssignments;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permission> permissions = new HashSet<>();
}
