package org.example.smartinventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.With;
import org.example.smartinventory.config.ConverterConfig;
import org.example.smartinventory.config.JpaConfig;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.service.StockService;
import org.example.smartinventory.workbench.converter.StockConverter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = StockController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({JpaConfig.class, ConverterConfig.class})
@RunWith(SpringRunner.class)
class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private StockConverter stockConverter;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ProductEntity productEntity;

    private ProductEntity product2;

    @BeforeEach
    void setUp() {
        productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setDescription("Product 1");
        productEntity.setPrice(new BigDecimal("100.00"));
        productEntity.setCategory("Electronics");
        productEntity.setSku("EL-CD342");
        productEntity.setQuantity(5);
        productEntity.setExpirationDate(LocalDate.of(2024, 5, 12));
        productEntity.setReorderPoint(10);
        productEntity.setNotes("Added today");

        product2 = new ProductEntity();
        product2.setId(2);
        product2.setName("Product 2");
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("50.00"));
        product2.setCategory("Electronics");
        product2.setSku("EL-GB233");
        product2.setQuantity(10);
        product2.setExpirationDate(LocalDate.of(2024, 6, 30));
        product2.setReorderPoint(30);
        product2.setNotes("Added today");

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser
    void testGetAllStocks() throws Exception {

        Collection<StockEntity> stocks = new ArrayList<>();
        stocks.add(new StockEntity());
        stocks.add(new StockEntity());

        when(stockService.findAll()).thenReturn(stocks);
        mvc.perform(get("/api/stock/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser
    void testFetchStockByProductId() throws Exception {
        int productId = 123;
        Collection<StockEntity> stocks = new ArrayList<>();
        stocks.add(new StockEntity());
        stocks.add(new StockEntity());

        when(stockService.getStocksByProductId(productId)).thenReturn(stocks);
        mvc.perform(get("/api/stock/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser
    void testUpdateStock() throws Exception {
        StockEntity stock = new StockEntity();
        Set<ProductEntity> products = new HashSet<>();
        products.add(productEntity);
        products.add(product2);
        stock.setId(1L);
        stock.setQuantity(10);
        stock.setProducts(products);
        when(stockService.updateStock(any(StockEntity.class))).thenReturn(stock);
        mvc.perform(put("/api/stock/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.quantity", Matchers.is(10)));
    }

    @Test
    @WithMockUser
    void testCreateStock() throws Exception{
        StockEntity stock = new StockEntity();
        stock.setId(1L);
        stock.setQuantity(10);
        Set<ProductEntity> products = new HashSet<>();
        products.add(productEntity);
        products.add(product2);
        stock.setProducts(products);

        mvc.perform(post("/api/stock/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.quantity", Matchers.is(10)));
    }

    @Test
    @WithMockUser
    void testDeleteStock() throws Exception{
        StockEntity stock = new StockEntity();
        stock.setId(1L);
        stock.setQuantity(10);
        when(stockService.findById(1L)).thenReturn(Optional.of(stock));

        mvc.perform(delete("/api/stock/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testStockAlert() throws Exception {

    }

    @AfterEach
    void tearDown() {
    }
}