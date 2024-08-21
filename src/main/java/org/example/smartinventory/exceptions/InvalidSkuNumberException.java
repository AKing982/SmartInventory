package org.example.smartinventory.exceptions;

public class InvalidSkuNumberException extends IllegalArgumentException
{
    public InvalidSkuNumberException(String s) {
        super("Invalid sku number: " + s);
    }
}
