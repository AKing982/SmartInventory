package org.example.smartinventory.exceptions;

public class InvalidSkuNumberSegmentException extends IllegalArgumentException
{
    public InvalidSkuNumberSegmentException(String s) {
        super("Invalid sku number segment: " + s);
    }
}
