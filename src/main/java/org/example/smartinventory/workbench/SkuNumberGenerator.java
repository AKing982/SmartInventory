package org.example.smartinventory.workbench;

import lombok.NoArgsConstructor;
import org.example.smartinventory.model.SkuNumber;
import org.example.smartinventory.service.SkuHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@NoArgsConstructor
@RequestScope
public class SkuNumberGenerator
{
    private final Map<String, AtomicInteger> sequenceCache = new ConcurrentHashMap<>();
    private Logger LOGGER = LoggerFactory.getLogger(SkuNumberGenerator.class);
    private SkuHistoryService skuHistoryService;

    @Autowired
    public SkuNumberGenerator(SkuHistoryService skuHistoryService){
        this.skuHistoryService = skuHistoryService;
    }

    public SkuNumber generateSkuNumber(String category)
    {
        if(category.isEmpty()){
            throw new IllegalArgumentException("Category cannot be empty");
        }
        String parsedCategory = generateCategoryCode(category);
        String supplierCode = generateSupplierCode(5);
        String key = parsedCategory + "-" + supplierCode;
        int sequence = generateSequenceNumber(key);;
        return new SkuNumber(parsedCategory, supplierCode);
    }

    public String generateSupplierCode(int length){
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }

        return sb.toString();
    }

    public String generateCategoryCode(String category){
        switch(category){
            case "Electronics":
                return "EL";
            case "Clothing":
                return "CO";
            case "Books":
                return "BK";
            case "Food":
                return "FD";
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

    public int generateSequenceNumber(String key){
        if(key.isEmpty()){
            throw new IllegalArgumentException("Key cannot be empty");
        }
        return sequenceCache.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
    }
    
}
