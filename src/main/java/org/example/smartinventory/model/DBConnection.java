package org.example.smartinventory.model;

import lombok.Data;

@Data
public class DBConnection
{
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
}
