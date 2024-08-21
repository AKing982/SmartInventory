package org.example.smartinventory.model;

import lombok.Getter;
import lombok.Setter;
import org.example.smartinventory.exceptions.InvalidCategorySegmentException;
import org.example.smartinventory.exceptions.InvalidSkuNumberException;
import org.example.smartinventory.exceptions.InvalidSkuNumberSegmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class SkuNumber
{
    private String categoryCode;
    private String supplierCode;

    public SkuNumber(String categoryCode, String supplierCode){
        initializeSkuNumber(categoryCode, supplierCode);
    }

    void initializeSkuNumber(String categoryCode, String supplierCode){
        this.categoryCode = categoryCode;
        this.supplierCode = supplierCode;
    }

    public Boolean validate(String categoryCode, String supplierCode){
        if(categoryCode.isEmpty() || supplierCode.isEmpty()){
            return false;
        }
        int categoryCodeLength = getLength(categoryCode);
        int supplierCodeLength = getLength(supplierCode);
        if(categoryCodeLength != 2 || supplierCodeLength != 5){
            return false;
        }else{
            Pattern characters = Pattern.compile("[a-zA-Z]*");
            Pattern numbers = Pattern.compile("[0-9]*");
            Matcher Charmatcher = characters.matcher(categoryCode);
            Matcher supplierMatcher = characters.matcher(supplierCode);
            if(!Charmatcher.matches() || !supplierMatcher.matches()){
                return false;
            }
        }
        return categoryCode.length() == 2 && supplierCode.length() == 5;
    }

    private int getLength(String strLength){
        return strLength.length();
    }

    @Override
    public String toString() {
        return "SkuNumber{" +
                "categoryCode='" + categoryCode + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                '}';
    }
}
