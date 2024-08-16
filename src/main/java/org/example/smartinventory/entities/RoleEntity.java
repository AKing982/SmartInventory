package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.Permission;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int id;

    @Column(name="role", unique = true)
    private String role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name="permission")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions = new HashSet<>();

}
