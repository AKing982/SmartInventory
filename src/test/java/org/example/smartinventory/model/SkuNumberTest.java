package org.example.smartinventory.model;

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
    void testValidateSegments_whenCatSegmentIsEmpty(){
        String catSegment = "";
        String numSegment = "001";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenNumSegmentIsEmpty(){
        String catSegment = "BOOK";
        String numSegment = "";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenCatSegmentIsLengthLessThanFour(){
        String catSegment = "BOO";
        String numSegment = "001";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenCatSegmentIsValid_returnTrue(){
        String catSegment = "BOOK";
        String numSegment = "001";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertTrue(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenNumSegmentIsNotEqualToThree(){
        String catSegment = "BOOK";
        String numSegment = "00";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenNumSegmentValid(){
        String catSegment = "BOOK";
        String numSegment = "001";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertTrue(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenCatSegmentNotCharacters(){
        String catSegment = "0232";
        String numSegment = "001";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @Test
    void testValidateSegments_whenNumSegmentNotNumeric(){
        String catSegment = "BOOK";
        String numSegment = "BOO";
        skuNumber = new SkuNumber(catSegment, numSegment);
        assertFalse(skuNumber.validateSegments(catSegment, numSegment));
    }

    @AfterEach
    void tearDown() {
    }
}