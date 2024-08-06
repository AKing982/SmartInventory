package org.example.smartinventory.controllers;

import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value="/api/products")
public class ProductController
{
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<Collection<ProductEntity>> getAllProducts()
    {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductEntity>> getProductById(@PathVariable("id") Long id){
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@RequestBody ProductEntity product){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductEntity product){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return null;
    }

}
