package org.example.smartinventory.workbench;

import org.example.smartinventory.model.SkuNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class SkuNumberGeneratorTest {

    @Autowired
    private SkuNumberGenerator skuNumberGenerator;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGenerateSkuNumber_whenCategoryIsEmpty(){
        String category = "";
        String supplier = "SUPPLIER";
        assertThrows(IllegalArgumentException.class, () -> skuNumberGenerator.generateSkuNumber(category, supplier));
    }

    @Test
    void testGenerateSkuNumber_whenSupplierIsEmpty(){
        String category = "BOOKS";
        String supplier = "";
        assertThrows(IllegalArgumentException.class, () -> skuNumberGenerator.generateSkuNumber(category, supplier));
    }

    @Test
    void testGenerateSkuNumber_ValidSku(){
        String category = "BOOKS";
        String supplier = "SUPPLIER";
        String expectedSku = "BOSUP0001";
        String actualSku = skuNumberGenerator.generateSkuNumber(category, supplier).toString();
        assertEquals(expectedSku, actualSku);
    }

    @Test
    void testGenerateSequenceNumber(){

    }

    @AfterEach
    void tearDown() {
    }
}