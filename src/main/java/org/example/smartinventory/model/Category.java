package org.example.smartinventory.model;

import lombok.Data;

import java.util.Set;

@Data
public class Category
{
    private Integer id;
    private String name;
    private String description;

    public Category(int id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
