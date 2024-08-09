package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryEntity testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new CategoryEntity();
        testCategory.setName("test");
        testCategory.setCategoryId(1);
        testCategory.setDescription("desc");
    }

    @Test
    void testFindAll()
    {
        List<CategoryEntity> list = Arrays.asList(testCategory);
        when(categoryRepository.findAll()).thenReturn(list);

        Collection<CategoryEntity> result = categoryService.findAll();
        assertNotNull(result);
        assertEquals(list.size(), result.size());
    }

    @Test
    void testSave()
    {
        categoryService.save(testCategory);

        verify(categoryRepository, times(1)).save(testCategory);
    }

    @Test
    void testDelete()
    {
        categoryService.delete(testCategory);

        verify(categoryRepository, times(1)).delete(testCategory);
    }

    @Test
    void testFindById()
    {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Optional<CategoryEntity> result = categoryService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testCategory, result.get());
    }

    @Test
    void testFindByName()
    {
        when(categoryRepository.findByName("test")).thenReturn(Optional.of(testCategory));

        Optional<CategoryEntity> result = categoryService.findByName("test");

        assertTrue(result.isPresent());
        assertEquals(testCategory, result.get());
    }

    @AfterEach
    void tearDown() {
    }
}