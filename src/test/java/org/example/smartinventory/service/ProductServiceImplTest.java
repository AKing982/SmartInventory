package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.model.Product;
import org.example.smartinventory.repository.CategoryRepository;
import org.example.smartinventory.repository.ProductRepository;
import org.example.smartinventory.workbench.converter.ProductDTOConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Mock
    private ProductDTOConverter productDTOConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity testProduct;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository,  categoryRepository);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        // Arrange
        List<ProductEntity> expectedProducts = List.of(
                new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDate.now()),
                new ProductEntity(2, "Product2", "Desc2", "SKU2", BigDecimal.ONE, 10, null, LocalDate.now())
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<ProductEntity> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(expectedProducts, actualProducts);
    }


    @Test
    void validateProduct_shouldReturnTrueForValidProduct() {
        // Arrange
        ProductEntity validProduct = new ProductEntity(1, "Valid Product", "Valid Desc", "VALID-SKU", new BigDecimal("9.99"), 10, null, LocalDate.now());
        when(productRepository.findById(1L)).thenReturn(Optional.of(validProduct));

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
        ProductEntity updatedProduct = new ProductEntity(1, "Product1", "Desc1", "SKU1", newPrice, 5, null, LocalDate.now());
        when(productRepository.updateProductPrice(1L, newPrice)).thenReturn(Optional.of(updatedProduct));

        // Act

        Optional<ProductEntity> result = productService.updateProductPrice(productId, newPrice);

        // Assert
        assertEquals(updatedProduct, result.get());
        assertEquals(newPrice, updatedProduct.getPrice());
        verify(productRepository).updateProductPrice(1L, newPrice);
    }

    @Test
    void deleteProduct_shouldDeleteProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, null, LocalDate.now());

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository).deleteById(productId);
    }



    @Test
    void updateProduct_shouldUpdateProductQuantity()
    {
        // Arrange
        int productId = 1;
        int newQuantity = 10;
        ProductEntity product = new ProductEntity(productId, "Product1", "Desc1", "SKU1", BigDecimal.ONE, newQuantity, null, LocalDate.now());

        // Act
        when(productRepository.updateProductQuantity(productId, newQuantity)).thenReturn(Optional.of(product));

        Optional<ProductEntity> actual = productService.updateProductQuantity(productId, newQuantity);
        assertTrue(actual.isPresent());
        assertEquals(newQuantity, actual.get().getQuantity());
    }

    @Test
    void updateProduct_shouldUpdateProductCategory()
    {
        Long productId = 1L;
        CategoryEntity newCategory = new CategoryEntity();
        ProductEntity updatedProduct = new ProductEntity(1, "Product1", "Desc1", "SKU1", BigDecimal.TEN, 5, "Electronics", LocalDate.now());
        when(productRepository.updateProductCategory(1L, newCategory)).thenReturn(Optional.of(updatedProduct));

        Optional<ProductEntity> result = productService.updateProductCategory(productId, newCategory);

        assertEquals(updatedProduct, result.get());
        assertEquals(newCategory, updatedProduct.getCategory());
        verify(productRepository).updateProductCategory(1L, newCategory);
    }

    @AfterEach
    void tearDown() {
    }
}