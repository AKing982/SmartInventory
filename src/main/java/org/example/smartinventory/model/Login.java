package org.example.smartinventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Login implements Serializable
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

    public Login(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
