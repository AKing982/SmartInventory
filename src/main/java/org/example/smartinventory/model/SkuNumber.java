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
    private String identifier;

    public SkuNumber(String categoryCode, String supplierCode){
        this.categoryCode = categoryCode;
        this.supplierCode = supplierCode;
    }

    public SkuNumber(String categoryCode, String supplierCode, String identifier){
        this.categoryCode = categoryCode;
        this.supplierCode = supplierCode;
        this.identifier = identifier;
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
