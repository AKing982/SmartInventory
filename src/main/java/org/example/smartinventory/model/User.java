package org.example.smartinventory.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class User
{
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private boolean isActive;

    public User(int userId, String firstName, String lastName, String email, String userName, String password, boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
    }
}
