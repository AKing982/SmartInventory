package org.example.smartinventory.service;

import org.example.smartinventory.entities.InventoryNotesEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService extends ServiceModel<OrderEntity>
{
    // Create
    OrderEntity createOrder(OrderEntity order);
    List<OrderEntity> createOrders(List<OrderEntity> orders);

    // Read
    Optional<OrderEntity> getOrderById(int orderId);
    Optional<OrderEntity> getOrderByOrderNumber(String orderNumber);
    List<OrderEntity> getAllOrders();
    Page<OrderEntity> getAllOrdersPaginated(Pageable pageable);

    // Update
    Optional<OrderEntity> updateOrder(OrderEntity order);
    Optional<OrderEntity> updateOrderStatus(int orderId, OrderStatus newStatus);
    Optional<OrderEntity> updateOrderShippingAddress(int orderId, String newShippingAddress);
    Optional<OrderEntity> updateOrderBillingAddress(int orderId, String newBillingAddress);

    // Delete
    void deleteOrder(int orderId);
    void deleteOrders(List<Integer> orderIds);

    // Search and Filter
    List<OrderEntity> searchOrders(String keyword);
    List<OrderEntity> getOrdersByStatus(OrderStatus status);
    List<OrderEntity> getOrdersByCustomer(int customerId);
    List<OrderEntity> getOrdersByCreatedByUser(int userId);
    List<OrderEntity> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Count
    long getOrderCount();
    long getOrderCountByStatus(OrderStatus status);
    long getOrderCountByCustomer(int customerId);

    // Validation
    boolean isOrderNumberUnique(String orderNumber);

    // Statistics
    double getTotalOrderValue();
    double getTotalOrderValueByCustomer(int customerId);
    double getTotalOrderValueByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Custom queries
    List<OrderEntity> getRecentOrders(int limit);
    List<OrderEntity> getHighValueOrders(double threshold);
    List<OrderEntity> getOrdersWithoutNotes();

    // Inventory Notes related
    Optional<OrderEntity> addNotesToOrder(int orderId, InventoryNotesEntity notes);
    Optional<OrderEntity> removeNotesFromOrder(int orderId);

    // Export
    byte[] exportOrdersToCsv();
    byte[] exportOrdersToPdf();

    // Import
    List<OrderEntity> importOrdersFromCsv(byte[] csvData);

    // Sorting
    List<OrderEntity> getAllOrdersSorted(String sortBy, String sortOrder);

    // Analytics
    List<OrderEntity> getMostFrequentCustomers(int limit);
    List<OrderEntity> getLeastFrequentCustomers(int limit);
    double getAverageOrderValue();
    OrderStatus getMostCommonOrderStatus();

    // Time-based queries
    List<OrderEntity> getOrdersCreatedToday();
    List<OrderEntity> getOrdersCreatedThisWeek();
    List<OrderEntity> getOrdersCreatedThisMonth();

    // Status transitions
    Optional<OrderEntity> processOrder(int orderId);
    Optional<OrderEntity> shipOrder(int orderId);
    Optional<OrderEntity> deliverOrder(int orderId);
    Optional<OrderEntity> cancelOrder(int orderId);

    // Bulk operations
    int bulkUpdateOrderStatus(List<Integer> orderIds, OrderStatus newStatus);

    // Reporting
    byte[] generateOrderReport(LocalDateTime startDate, LocalDateTime endDate);
}
