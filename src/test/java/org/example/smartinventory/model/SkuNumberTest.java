package org.example.smartinventory.model;

import org.example.smartinventory.exceptions.InvalidSkuNumberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

class SkuNumberTest {

    private SkuNumber skuNumber;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testConstructor(){
        skuNumber = new SkuNumber("AB", "CRT", "0012");
        assertEquals("AB", skuNumber.getCategoryCode());
        assertEquals("CRT", skuNumber.getSupplierCode());
        assertEquals("0012", skuNumber.getSequenceCode());
    }

    @Test
    void testValidate_WhenCategoryCodeIsEmpty(){
        String categoryCode = "";
        String supplierCode = "CRT";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSupplierCodeIsEmpty(){
        String categoryCode = "AB";
        String supplierCode = "";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSequenceCodeIsEmpty(){
        String categoryCode = "AB";
        String supplierCode = "CRT";
        String sequenceCode = "";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenCategoryCodeSizeIsNotEqualToTwo(){
        String categoryCode = "ABC";
        String supplierCode = "CRT";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSupplierCodeSizeIsNotEqualToThree(){
        String categoryCode = "AB";
        String supplierCode = "CRTE";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSequenceCodeIsNotFourDigits(){
        String categoryCode = "AB";
        String supplierCode = "CRT";
        String sequenceCode = "001234";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenCategoryCodeIsNotCharacter(){
        String categoryCode = "02";
        String supplierCode = "CRT";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSupplierCodeIsNotLetters(){
        String categoryCode = "AB";
        String supplierCode = "002";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_whenSequenceCodeIsNotDigits(){
        String categoryCode = "AB";
        String supplierCode = "CRT";
        String sequenceCode = "ABCE";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_ValidSkuNumber(){
        String categoryCode = "AB";
        String supplierCode = "CRT";
        String sequenceCode = "0012";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertTrue(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testValidate_ValidLength_InvalidSkuNumber(){
        String categoryCode = "92";
        String supplierCode = "ABC";
        String sequenceCode = "AB02";
        skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        assertFalse(skuNumber.validate(categoryCode, supplierCode, sequenceCode));
    }

    @Test
    void testConstructor_InvalidCodes(){
        String categoryCode = "92";
        String supplierCode = "ABC";
        String sequenceCode = "AB02";
        assertThrows(InvalidSkuNumberException.class, () -> {
            skuNumber = new SkuNumber(categoryCode, supplierCode, sequenceCode);
        });
    }

    @AfterEach
    void tearDown() {
    }
}