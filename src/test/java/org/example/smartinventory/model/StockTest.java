package org.example.smartinventory.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private Stock testStock;

    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    void setUp() {
        Map<Product, Integer> productMap = new HashMap<>();
        testProduct1 = new Product(1, "Product 1", "Description 1", new BigDecimal("10.00"), 5, "Category 1", "SKU1", "Brand 1", new BigDecimal("8.00"), LocalDate.now().plusMonths(6), "Added", 3, "Supplier 1", "Model1", 20.0);
        testProduct2 = new Product(2, "Product 2", "Description 2", new BigDecimal("20.00"), 3, "Category 2", "SKU2", "Brand 2", new BigDecimal("15.00"), LocalDate.now().plusMonths(12), "Added", 5, "Supplier 2", "Model2", 25.0);
        productMap.put(testProduct1, 5);
        productMap.put(testProduct2, 3);

        testStock = new Stock(1L, productMap, false, LocalDateTime.now());
    }

    @Test
    void testAddProduct(){
        Product newProduct = new Product(3, "New Product", "New Description", new BigDecimal("15.00"), 0, "New Category", "SKU3", "New Brand", new BigDecimal("12.00"), LocalDate.now().plusMonths(9), "Added", 4, "New Supplier", "Model3", 22.0);
        testStock.addProduct(newProduct, 2);
        assertEquals(3, testStock.getProductQuantityMap().size());
        assertEquals(2, testStock.getProductQuantityMap().get(newProduct));
    }

    @Test
    void testRemoveProduct(){
        testStock.removeProduct(testProduct1, 2);
        assertEquals(3, testStock.getProductQuantityMap().get(testProduct1));

        testStock.removeProduct(testProduct2, 3);
        assertFalse(testStock.getProductQuantityMap().containsKey(testProduct2));
    }

    @Test
    void testCheckLowStock(){
        int threshold = 1;
        testStock.checkLowStock(threshold);
        assertFalse(testStock.isLow());

        testStock.checkLowStock(9);
        assertFalse(testStock.isLow());
    }


    @AfterEach
    void tearDown() {
    }
}