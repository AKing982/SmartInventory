package org.example.smartinventory.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShipmentInfo
{
    private LocalDate shippedDate;
    private LocalDate deliveryDate;
    private TrackingNumber trackingNumber;
    private ShippingMethod shippingMethod;
    private String carrierName;
    private BigDecimal shippingCost;
    private Address shippingAddress;
    private List<Notes> shippingNotes = new ArrayList<>();
}
