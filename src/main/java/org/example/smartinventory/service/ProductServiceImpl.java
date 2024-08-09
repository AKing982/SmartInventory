package org.example.smartinventory.service;

import org.example.smartinventory.entities.CategoryEntity;
import org.example.smartinventory.entities.ProductEntity;
import org.example.smartinventory.repository.CategoryRepository;
import org.example.smartinventory.repository.ProductRepository;
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
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> assignCategory(Long productId, String category) {
        return Optional.empty();
    }

    @Override
    public boolean validateProduct(ProductEntity product) {
        return false;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteProducts(List<Long> productIds) {
        productIds.forEach(productRepository::deleteById);
    }

    @Override
    public int updateProductPrice(Long productId, BigDecimal newPrice) {
        return productRepository.updateProductPrice(productId, newPrice);
    }

    @Override
    public int updateProductQuantity(Long productId, int newQuantity) {
        return productRepository.updateProductQuantity(productId, newQuantity);
    }

    @Override
    public int updateProductCategory(Long productId, CategoryEntity newCategory) {
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
