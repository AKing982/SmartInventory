package org.example.smartinventory.dto;

import org.example.smartinventory.model.Product;

import java.util.List;
import java.util.Set;

public record StockDTO(Long id, boolean isLow, List<Product> products, int quantity) {
}
