package org.example.smartinventory.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.smartinventory.dto.ProductDTO;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.service.ProductService;
import org.example.smartinventory.service.SkuHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value="/api/products")
public class ProductController
{
    private ProductService productService;
    private SkuHistoryService skuHistoryService;
    private Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService,
                             SkuHistoryService skuHistoryService)
    {
        this.productService = productService;
        this.skuHistoryService = skuHistoryService;
    }

    @GetMapping(value="/", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllProducts()
    {
        Collection<ProductEntity> products = productService.getAllProducts();
        if(products.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        Optional<ProductEntity> productEntity = productService.findById(id);
        if(productEntity.isPresent())
        {
            ProductEntity product = productEntity.get();
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product){
        try
        {
            LOGGER.info("Adding ProductDTO: {}", product);
            productService.addProductDTO(product);
            LOGGER.info("Product has been added to the database.");
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }catch(Exception e) {
            LOGGER.error("There was an internal error saving the product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){

        try
        {
            if(id == null) {
                return ResponseEntity.badRequest().body("ProductID cannot be null");
            }

            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            LOGGER.error("There was an internal error deleting the product with id: {}, {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
