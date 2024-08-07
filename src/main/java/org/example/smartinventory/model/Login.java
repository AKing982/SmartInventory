package org.example.smartinventory.model;

import lombok.Data;

@Data
public class Login
{
    private int userId;
    private String username;
    private String password;

    public Login(int userId, String username, String password)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}
