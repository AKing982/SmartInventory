package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.repository.CategoryRepository;
import org.example.smartinventory.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity testProduct;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        // Arrange
        List<ProductEntity> expectedProducts = List.of(
                new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDateTime.now()),
                new ProductEntity(2, "Product2", "Desc2", "SKU2", BigDecimal.ONE, 10, null, LocalDateTime.now())
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<ProductEntity> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository).findAll();
    }

    @Test
    void assignCategory_shouldAssignCategoryToProduct() {
        // Arrange
        Long productId = 1L;
        String categoryName = "Electronics";
        ProductEntity product = new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDateTime.now());
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        // Act
        Optional<ProductEntity> result = productService.assignCategory(productId, categoryName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(category, result.get().getProductCategory());
        verify(productRepository).findById(productId);
        verify(categoryRepository).findByName(categoryName);
        verify(productRepository).save(product);
    }

    @Test
    void validateProduct_shouldReturnTrueForValidProduct() {
        // Arrange
        ProductEntity validProduct = new ProductEntity(1, "Valid Product", "Valid Desc", "VALID-SKU", new BigDecimal("9.99"), 10, null, LocalDateTime.now());

        // Act
        boolean isValid = productService.validateProduct(validProduct);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void updateProductPrice_shouldUpdatePriceSuccessfully() {
        // Arrange
        Long productId = 1L;
        BigDecimal newPrice = new BigDecimal("15.99");
        ProductEntity product = new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        // Act
        int result = productService.updateProductPrice(productId, newPrice);

        // Assert
        assertEquals(1, result);
        assertEquals(newPrice, product.getProductPrice());
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_shouldDeleteProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);
    }

    @AfterEach
    void tearDown() {
    }
}