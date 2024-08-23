package org.example.smartinventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.example.smartinventory.config.JpaConfig;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = StockController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({JpaConfig.class})
@RunWith(SpringRunner.class)
class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockService stockService;

    @MockBean
    private StockConverter stockConverter;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
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
        stock.setId(1L);
        stock.setQuantity(10);
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
    void testStockAlert() throws Exception {

    }

    @AfterEach
    void tearDown() {
    }
}