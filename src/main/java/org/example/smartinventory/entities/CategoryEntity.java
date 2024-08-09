package org.example.smartinventory.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@JsonIdentityInfo(        // Add this line
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "categoryId"
)
public class CategoryEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String name;

    private String description;

    public CategoryEntity(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }
}

