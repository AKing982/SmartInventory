package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Entity
@Table(name="categories")
@Data
public class CategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @NotBlank(message="Category Name is required")
    private String name;

    private String description;
}

