package org.example.smartinventory.model;

import lombok.Getter;
import lombok.Setter;
import org.example.smartinventory.exceptions.InvalidCategorySegmentException;
import org.example.smartinventory.exceptions.InvalidSkuNumberException;
import org.example.smartinventory.exceptions.InvalidSkuNumberSegmentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class SkuNumber
{
    private String categoryCode;
    private String supplierCode;
    private String sequenceCode;

    public SkuNumber(String categoryCode, String supplierCode, String sequenceCode){
        initializeSkuNumber(categoryCode, supplierCode, sequenceCode);
    }

    void initializeSkuNumber(String categoryCode, String supplierCode, String sequenceCode){
        if(validate(categoryCode, supplierCode, sequenceCode)){
            this.categoryCode = categoryCode;
            this.supplierCode = supplierCode;
            this.sequenceCode = sequenceCode;
        }else{
            throw new InvalidSkuNumberException(categoryCode + "/" + supplierCode + "/" + sequenceCode);
        }
    }

    public Boolean validate(String categoryCode, String supplierCode, String sequenceCode){
        if(categoryCode.isEmpty() || supplierCode.isEmpty() || sequenceCode.isEmpty()){
            return false;
        }
        int categoryCodeLength = getLength(categoryCode);
        int supplierCodeLength = getLength(supplierCode);
        int sequenceCodeLength = getLength(sequenceCode);
        if(categoryCodeLength != 2 || supplierCodeLength != 3 || sequenceCodeLength != 4){
            return false;
        }else{
            Pattern characters = Pattern.compile("[a-zA-Z]*");
            Pattern numbers = Pattern.compile("[0-9]*");
            Matcher matcher = characters.matcher(categoryCode);
            Matcher supplierMatcher = characters.matcher(supplierCode);
            Matcher sequenceMatcher = numbers.matcher(sequenceCode);
            if(!matcher.matches() || !supplierMatcher.matches() || !sequenceMatcher.matches()){
                return false;
            }
        }
        return categoryCode.length() == 2 && supplierCode.length() == 3 && sequenceCode.length() == 4;
    }

    private int getLength(String strLength){
        return strLength.length();
    }
}
