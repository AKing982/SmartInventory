package org.example.smartinventory.model;

import lombok.Data;

@Data
public class TrackingNumber
{
    private String prefix;
    private String number;
    private String suffix;

    public TrackingNumber(String prefix, String number, String suffix) {
        if(number == null || number.isEmpty())
        {
            throw new IllegalArgumentException("Tracking Number cannot be empty or null");
        }
        this.prefix = prefix.trim();
        this.number = number.trim();
        this.suffix = suffix.trim();
    }

    public static TrackingNumber Parse(String trackingParam)
    {
        if(trackingParam == null || trackingParam.isEmpty())
        {
            throw new IllegalArgumentException("Unable to parse empty Tracking parameter.");
        }

        var segments = trackingParam.split("-");
        switch(segments.length)
        {
            case 1:
                return new TrackingNumber(null,segments[1],null);
            case 2:
                return new TrackingNumber(segments[0],segments[1], null);
            case 3:
                return new TrackingNumber(segments[0],segments[1],segments[2]);
            default:
                throw new IllegalArgumentException("Unable to parse Tracking Parameter " + trackingParam);
        }
    }
}
