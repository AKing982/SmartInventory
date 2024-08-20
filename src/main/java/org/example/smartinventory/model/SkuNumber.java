package org.example.smartinventory.model;

import org.example.smartinventory.exceptions.InvalidCategorySegmentException;
import org.example.smartinventory.exceptions.InvalidSkuNumberSegmentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkuNumber
{
    private String categorySegment;
    private String numSegment;

    public SkuNumber(String seg1, String seg2){
        initializeSkuNumber(seg1, seg2);
    }

    void initializeSkuNumber(String seg1, String seg2){
        if(validateSegments(seg1, seg2)){
            this.categorySegment = seg1;
            this.numSegment = seg2;
        }
    }

    public Boolean validateSegments(String catSegment, String numSegment){
        int catLength = catSegment.length();
        int numLength = numSegment.length();
        boolean isSegmentsEmpty = catSegment.isEmpty() || numSegment.isEmpty();
        if((catLength != 4 || numLength != 3) || isSegmentsEmpty){
            return false;
        }else{
            Pattern alphaNumPattern = Pattern.compile("^[a-zA-Z]*$");
            Pattern numericPattern = Pattern.compile("^[0-9]*$");
            Matcher alphaMatcher = alphaNumPattern.matcher(catSegment);
            Matcher numericMatcher = numericPattern.matcher(numSegment);
            if(!alphaMatcher.matches() || !numericMatcher.matches()){
                return false;
            }
        }
        return catSegment.length() == 4 && numSegment.length() == 3;
    }
}
