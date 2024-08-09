package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;

import java.util.Optional;

public interface CategoryService extends ServiceModel<CategoryEntity>
{
    Optional<CategoryEntity> findByName(String name);

}
