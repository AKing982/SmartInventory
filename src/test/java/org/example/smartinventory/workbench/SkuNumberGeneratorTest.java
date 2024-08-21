package org.example.smartinventory.workbench;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.example.smartinventory.model.SkuNumber;
import org.example.smartinventory.service.SkuHistoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkuNumberGeneratorTest {

    @InjectMocks
    private SkuNumberGenerator skuNumberGenerator;

    @Mock
    private SkuHistoryService skuHistoryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGenerateSkuNumber_whenCategoryIsEmpty(){
        String category = "";
        assertThrows(IllegalArgumentException.class, () -> skuNumberGenerator.generateSkuNumber(category));
    }

    @Test
    void testGenerateSkuNumber_whenCategoryIsUpperCase(){
        String category = "ELECTRONICS";
        SkuNumber expectedSkuNumber = new SkuNumber("EL", "CD532");
        SkuNumber actual = skuNumberGenerator.generateSkuNumber(category);
        assertNotEquals(expectedSkuNumber.toString(), actual.toString());
        assertEquals("EL", actual.getCategoryCode());
        assertNotEquals("CD532", actual.getSupplierCode());
    }

    @Test
    void testGenerateSkuNumber_whenCategoryIsLowerCase(){
        String category = "electronics";
        SkuNumber expectedSkuNumber = new SkuNumber("EL", "CD532");
        SkuNumber actual = skuNumberGenerator.generateSkuNumber(category);
        assertNotEquals(expectedSkuNumber.toString(), actual.toString());
        assertEquals("EL", actual.getCategoryCode());
        assertNotEquals("CD532", actual.getSupplierCode());
    }

    @Test
    void testValidateGeneratedSku_WhenCategoryCodeEmpty(){
        String category = "";
        String supplierCode = "CD532";
        assertThrows(IllegalArgumentException.class, () -> skuNumberGenerator.validateGeneratedSku(category, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_WhenSupplierCodeEmpty(){
        String category = "ELECTRONICS";
        String supplierCode = "";
        assertThrows(IllegalArgumentException.class, () -> skuNumberGenerator.validateGeneratedSku(category, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_CategoryCodeLengthNotTwo(){
        String categoryCode = "CLOTHING";
        String supplierCode = "CD532";
        assertFalse(skuNumberGenerator.validateGeneratedSku(categoryCode, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_WhenSupplierCodeLengthNotFive(){
        String categoryCode = "CL";
        String supplierCode = "CD53220";
        assertFalse(skuNumberGenerator.validateGeneratedSku(categoryCode, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_WhenSupplierCodeIsAllLetters(){
        String categoryCode = "CL";
        String supplierCode = "CDAAA";
        assertFalse(skuNumberGenerator.validateGeneratedSku(categoryCode, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_WhenCategoryCodeAllDigits(){
        String categoryCode = "53";
        String supplierCode = "CD532";
        assertFalse(skuNumberGenerator.validateGeneratedSku(categoryCode, supplierCode));
    }

    @Test
    void testValidateGeneratedSku_whenCategoryCodeNotAlpabetic(){
        String category = "1234542";
        String supplierCode = "CD532";
        assertFalse(skuNumberGenerator.validateGeneratedSku(category, supplierCode));
    }





    @AfterEach
    void tearDown() {
    }
}