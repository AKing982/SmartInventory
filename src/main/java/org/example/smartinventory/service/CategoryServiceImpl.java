package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void delete(CategoryEntity categoryEntity) {
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Collection<CategoryEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }

    @Override
    public Optional<CategoryEntity> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
