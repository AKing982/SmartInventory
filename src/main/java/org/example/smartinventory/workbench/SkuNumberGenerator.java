package org.example.smartinventory.workbench;

import lombok.NoArgsConstructor;
import org.example.smartinventory.model.Category;
import org.example.smartinventory.model.SkuNumber;
import org.example.smartinventory.repository.SkuHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@NoArgsConstructor
@RequestScope
public class SkuNumberGenerator
{
    private final Map<String, AtomicInteger> sequenceCache = new ConcurrentHashMap<>();
    private SkuHistoryRepository skuHistoryRepository;
    private final Set<SkuNumber> skuNumbers = new HashSet<>();

    @Autowired
    public SkuNumberGenerator(SkuHistoryRepository skuHistoryRepository){
        this.skuHistoryRepository = skuHistoryRepository;
    }

    public SkuNumber generateSkuNumber(String category, String supplier)
    {
        if(category.isEmpty() || supplier.isEmpty()){
            throw new IllegalArgumentException("Category cannot be empty");
        }
        String parsedCategory = parseSubstring(category, 0, 2);
        String parsedSupplier = parseSubstring(supplier, 0, 3);
        String key = parsedCategory + "-" + parsedSupplier;
        int sequence = generateSequenceNumber(key);
        storeSequenceNumberToCache(convertIntToAtomic(sequence));
        String sequenceStr = String.valueOf(sequence);
        SkuNumber skuNumber = new SkuNumber(parsedCategory, parsedSupplier, sequenceStr);
        skuNumbers.add(skuNumber);
        return skuNumber;
    }

    private AtomicInteger convertIntToAtomic(int sequence){
        return new AtomicInteger(sequence);
    }

    private String parseSubstring(String str, int start, int end){
        return str.substring(start, end);
    }

    private void storeSequenceNumberToCache(AtomicInteger sequenceNumber){
        sequenceCache.put(sequenceNumber.toString(), sequenceNumber);
    }

    public int updateLastSequence(String key, int sequence){
        return 0;
//        return skuHistoryRepository.findLastSequence(key).orElse(0);
    }

    public int getLastSequence(String key){
//        if(isNumeric(key)){
//            return skuHistoryRepository.findLastSequence(key).orElse(0);
//        }
        return 0;
    }

    public int generateSequenceNumber(String key){
        return 0;
//        if(isNumeric(key)){
//            AtomicInteger sequenceNumber = sequenceCache.computeIfAbsent(key, k -> new AtomicInteger(getLastSequence(k)));
//            int newSequence = sequenceNumber.incrementAndGet();
//            updateLastSequence(key, newSequence);
//            return newSequence;
//        }
//        return 0;
    }
    
}
