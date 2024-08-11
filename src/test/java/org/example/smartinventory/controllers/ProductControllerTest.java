package org.example.smartinventory.controllers;

import org.example.smartinventory.config.JpaConfig;
import org.example.smartinventory.entities.CategoryEntity;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({JpaConfig.class})
@RunWith(SpringRunner.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private String testCategory = "Electronics";

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
        when(productService.getAllProducts()).thenReturn(products);

        mvc.perform(get("/api/products/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser
    void testGetProductById() throws Exception {
        ProductEntity p = new ProductEntity();

        when(productService.findById(Mockito.anyLong())).thenReturn(Optional.of(p));

        mvc.perform(get("/api/products/" + testId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAddProduct() throws Exception {
        ProductEntity p = new ProductEntity();
        p.setId(1);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("Electronics");
        p.setCategory("Electronics");
        p.setPrice(BigDecimal.TEN);
        p.setSku("1919191919");
        p.setDescription("Electronics");
        p.setDateAdded(LocalDate.now());

        mvc.perform(post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(p)))
                .andExpect(status().isCreated());

        verify(productService, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testDeleteProductById() throws Exception {
        ProductEntity p = createProduct();
        int id = p.getId();

        mvc.perform(delete("/api/products/" + id))
                .andExpect(status().isOk());

    }

    private ProductEntity updatedProduct(BigDecimal price)
    {
        ProductEntity p = new ProductEntity();
        p.setId(1);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("Electronics");
        p.setCategory(testCategory);
        p.setPrice(price);
        p.setSku("1919191919");
        p.setDescription("Electronics");
        p.setDateAdded(LocalDate.now());
        return p;
    }

    private ProductEntity createProduct() {
        ProductEntity p = new ProductEntity();
        p.setId(1);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("Electronics");
        p.setCategory(testCategory);
        p.setPrice(BigDecimal.TEN);
        p.setSku("1919191919");
        p.setDescription("Electronics");
        p.setDateAdded(LocalDate.now());
        return p;
    }

    private static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }
}