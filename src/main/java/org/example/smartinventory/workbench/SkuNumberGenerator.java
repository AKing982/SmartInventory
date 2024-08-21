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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
@RequestScope
public class SkuNumberGenerator
{
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
        if(validateGeneratedSku(parsedCategory, supplierCode)){
            SkuNumber skuNumber = new SkuNumber(parsedCategory, supplierCode);
            LOGGER.info("Sku Number: {}", skuNumber);
            return skuNumber;
        }else{
            throw new IllegalArgumentException("Invalid sku code: " + parsedCategory + "-" + supplierCode);
        }
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

    public Boolean validateGeneratedSku(String categoryCode, String supplierCode){
        LOGGER.info("Category Code: {}", categoryCode);
        LOGGER.info("Supplier Code: {}", supplierCode);
        if(categoryCode.isEmpty() || supplierCode.isEmpty()){
            throw new IllegalArgumentException("Category cannot be empty");
        }
        if(categoryCode.length() != 2 || supplierCode.length() != 5){
            LOGGER.info("Category Code or Supplier Code length not valid");
            return false;
        }else{
            Pattern allLettersPattern = Pattern.compile("[A-Z]*");
            Matcher allLettersMatcher = allLettersPattern.matcher(categoryCode);
            if(!allLettersMatcher.matches()){
                LOGGER.info("Category Code matcher failed");
                return false;
            }
        }

        return categoryCode.length() == 2 && supplierCode.length() == 5;
    }

    public String generateCategoryCode(String category){
        String lowerCaseCategory = convertToLower(category);
        System.out.println(lowerCaseCategory);
        return switch (lowerCaseCategory) {
            case "Electronics" -> "EL";
            case "Clothing" -> "CO";
            case "Books" -> "BK";
            case "Food" -> "FD";
            default -> throw new IllegalArgumentException("Invalid category");
        };
    }

    public String convertToLower(String category){
       return category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
    }
}
