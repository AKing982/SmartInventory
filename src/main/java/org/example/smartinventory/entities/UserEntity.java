package org.example.smartinventory.entities;

import jakarta.persistence.*;
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
    private int userId;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private boolean isActive;

    public UserEntity(int userId, String firstName, String lastName, String email, String username, String password, boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }


}
