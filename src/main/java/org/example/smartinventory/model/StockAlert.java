package org.example.smartinventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockAlert extends Notification
{
    private Stock stock;
    private double priceThreshold;
    private LocalDateTime alertTime;
    private AlertType alertType;

    public StockAlert(String title, String content, String subject, String sender, int priority, boolean isRead, Stock stock, double priceThreshold, LocalDateTime alertTime, AlertType alertType) {
        super(title, content, subject, sender, priority, isRead);
        this.stock = stock;
        this.priceThreshold = priceThreshold;
        this.alertTime = alertTime;
        this.alertType = alertType;
    }

    @Override
    public String toString() {
        return "StockAlert{" +
                "stock=" + stock +
                ", priceThreshold=" + priceThreshold +
                ", alertTime=" + alertTime +
                ", alertType=" + alertType +
                '}';
    }
}
