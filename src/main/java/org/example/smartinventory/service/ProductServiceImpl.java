package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.repository.CategoryRepository;
import org.example.smartinventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        LOGGER.debug(products.toString());
        return productRepository.findAll();
    }


    @Override
    public boolean validateProduct(ProductEntity product) {
        Long productId = (long) product.getProductId();
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        return productEntity.isPresent();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity product, int productId) {
        return productRepository.updateProduct(productId, product)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Optional<ProductEntity> updateProductPrice(Long productId, BigDecimal newPrice) {
        return productRepository.updateProductPrice(productId, newPrice);
    }

    @Override
    public Optional<ProductEntity> updateProductQuantity(int productId, int newQuantity) {
        return productRepository.updateProductQuantity(productId, newQuantity);
    }

    @Override
    public Optional<ProductEntity> updateProductCategory(Long productId, CategoryEntity newCategory) {
        return productRepository.updateProductCategory(productId, newCategory);
    }

    @Override
    public Collection<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public void delete(ProductEntity productEntity) {
        productRepository.delete(productEntity);
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Collection<ProductEntity> findAllById(Iterable<Long> ids) {
        return List.of();
    }
}
