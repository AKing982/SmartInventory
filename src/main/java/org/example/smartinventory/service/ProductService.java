package org.example.smartinventory.service;

import org.example.smartinventory.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService extends ServiceModel<ProductEntity>
{
    // Create
    ProductEntity createProduct(ProductEntity product);
    List<ProductEntity> createProducts(List<ProductEntity> products);

    // Read
    Optional<ProductEntity> getProductBySKU(String sku);
    Page<ProductEntity> getAllProductsPaginated(Pageable pageable);

    // Update
    Optional<ProductEntity> updateProduct(ProductEntity product);
    Optional<ProductEntity> updateProductPrice(int productId, BigDecimal newPrice);
    Optional<ProductEntity> updateProductQuantity(int productId, int newQuantity);
    Optional<ProductEntity> updateProductCategory(int productId, String newCategory);

    // Delete
    void deleteProduct(int productId);
    void deleteProducts(List<Integer> productIds);

    // Search and Filter
    List<ProductEntity> searchProducts(String keyword);
    List<ProductEntity> getProductsByCategory(String category);
    List<ProductEntity> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<ProductEntity> getProductsWithLowStock(int threshold);

    // Count
    long getProductCount();
    long getProductCountByCategory(String category);

    // Validation
    boolean isSKUUnique(String sku);

    // Statistics
    BigDecimal getTotalInventoryValue();
    double getAverageProductPrice();
    ProductEntity getMostExpensiveProduct();
    ProductEntity getLeastExpensiveProduct();

    // Custom queries
    List<ProductEntity> getTopSellingProducts(int limit);
    List<ProductEntity> getRecentlyAddedProducts(int limit);
    List<ProductEntity> getProductsNeedingRestock(int threshold);

    // Export
    byte[] exportProductsToCsv();
    byte[] exportProductsToPdf();

    // Import
    List<ProductEntity> importProductsFromCsv(byte[] csvData);

    // Sorting
    List<ProductEntity> getAllProductsSorted(String sortBy, String sortOrder);

    // Analytics
    List<String> getTopCategories(int limit);
    BigDecimal getTotalValueByCategory(String category);

    // Time-based queries
    List<ProductEntity> getProductsAddedAfterDate(LocalDateTime date);
    List<ProductEntity> getProductsAddedLastMonth();

    // Bulk operations
    int bulkUpdateProductPrices(List<Integer> productIds, BigDecimal percentageIncrease);
    int bulkUpdateProductCategories(List<Integer> productIds, String newCategory);

    // Inventory management
    Optional<ProductEntity> increaseStock(int productId, int quantity);
    Optional<ProductEntity> decreaseStock(int productId, int quantity);
    boolean isInStock(int productId);

    // Reporting
    byte[] generateInventoryReport();
    byte[] generatePricingReport();

    // Category management
    List<String> getAllCategories();
    void addCategory(String category);
    void removeCategory(String category);

    // Price history
//    List<PriceHistoryEntry> getPriceHistory(int productId);
    void addPriceHistoryEntry(int productId, BigDecimal oldPrice, BigDecimal newPrice);

    // Product comparisons
    List<ProductEntity> getSimilarProducts(int productId, int limit);

    // Batch operations
    void batchCreateProducts(List<ProductEntity> products);
    void batchUpdateProducts(List<ProductEntity> products);

    // Advanced search
    Page<ProductEntity> advancedSearch(String name, String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
