package org.example.smartinventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartinventory.model.Permission;

@Table(name="users")
@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name", unique=true)
    private String firstName;

    @Column(name="last_name", unique=true)
    private String lastName;

    @Column(name="email", unique=true)
    private String email;

    @NotBlank
    @Column(name="username")
    private String username;

    @Column(name="password", unique=true)
    private String password;

    @Column(name="is_active")
    private boolean isActive;

    public UserEntity(int userId, String firstName, String lastName, String email, String username, String password, boolean isActive) {
        this.id = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }


}
