package org.example.smartinventory.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Stock
{
    private Long id;
    private Map<Product, Integer> productQuantityMap;
    private boolean isLow;
    private LocalDateTime lastUpdated;

    public Stock(Long id, Map<Product, Integer> productQuantityMap, boolean isLow, LocalDateTime lastUpdated) {
        this.id = id;
        this.productQuantityMap = productQuantityMap;
        this.isLow = isLow;
        this.lastUpdated = lastUpdated;
    }

    public void addProduct(Product product, int quantity) {
        if(product != null && quantity > 0){
            productQuantityMap.merge(product, quantity, Integer::sum);
        }
    }

    public void removeProduct(Product product, int quantity) {
        if(productQuantityMap.containsKey(product)) {
            int currentQuantity = productQuantityMap.get(product);
            if(currentQuantity <= quantity) {
                productQuantityMap.remove(product);
            }else{
                productQuantityMap.put(product, currentQuantity - quantity);
            }
            updateLastModified();
        }
    }

    public int getTotalStockCount(){
        return productQuantityMap.values()
                .stream()
                .mapToInt(Integer::intValue).sum();
    }

    public void checkLowStock(int threshold){
        int currentQuantity = getTotalStockCount();
        if(currentQuantity < threshold){
            isLow = true;
        }
    }

    private void updateLastModified(){
        this.lastUpdated = LocalDateTime.now();
    }
}
