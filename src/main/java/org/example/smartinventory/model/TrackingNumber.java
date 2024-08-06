package org.example.smartinventory.model;

public class TrackingNumber
{
    private String prefix;
    private String number;
    private String suffix;

    public TrackingNumber(String prefix, String number, String suffix) {
        this.prefix = prefix;
        this.number = number;
        this.suffix = suffix;
    }
}
