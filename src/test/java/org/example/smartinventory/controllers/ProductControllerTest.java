package org.example.smartinventory.controllers;

import org.example.smartinventory.config.JpaConfig;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(value = ProductController.class)
@Import({JpaConfig.class})
@RunWith(SpringRunner.class)

class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private int testId = 1;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser
    void testGetAllProducts() throws Exception {
        List<ProductEntity> products = new ArrayList<>();
        products.add(new ProductEntity());
        products.add(new ProductEntity());
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mvc.perform(get("/api/products/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser
    void testGetProductById() throws Exception {
        ProductEntity p = new ProductEntity();

        Mockito.when(productService.findById(Mockito.anyLong())).thenReturn(Optional.of(p));

        mvc.perform(get("/api/products/" + testId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterEach
    void tearDown() {
    }
}