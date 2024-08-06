package org.example.smartinventory.repository;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.model.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
    @Query("SELECT o FROM OrderEntity o WHERE o.orderNumber = :orderNumber")
    Optional<OrderEntity> findByOrderNumber(@Param("orderNumber") String orderNumber);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderStatus = :status")
    List<OrderEntity> findByStatus(@Param("status") OrderStatus status);

    @Query("SELECT o FROM OrderEntity o WHERE o.customer.customerId = :customerId")
    List<OrderEntity> findByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdByUser.userId = :userId")
    List<OrderEntity> findByCreatedByUserId(@Param("userId") int userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderStatus = :status")
    long countByStatus(@Param("status") OrderStatus status);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.customer.customerId = :customerId")
    long countByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT SUM(o.totalAmount) FROM OrderEntity o")
    double getTotalOrderValue();

    @Query("SELECT SUM(o.totalAmount) FROM OrderEntity o WHERE o.customer.customerId = :customerId")
    double getTotalOrderValueByCustomer(@Param("customerId") int customerId);

    @Query("SELECT SUM(o.totalAmount) FROM OrderEntity o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    double getTotalOrderValueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o FROM OrderEntity o ORDER BY o.orderDate DESC")
    List<OrderEntity> findRecentOrders(Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE o.totalAmount > :threshold")
    List<OrderEntity> findHighValueOrders(@Param("threshold") double threshold);

    @Query("SELECT o FROM OrderEntity o WHERE o.inventoryNotes IS NULL")
    List<OrderEntity> findOrdersWithoutNotes();

    @Query("SELECT o.customer, COUNT(o) as orderCount FROM OrderEntity o GROUP BY o.customer ORDER BY orderCount DESC")
    List<Object[]> findMostFrequentCustomers(Pageable pageable);

    @Query("SELECT AVG(o.totalAmount) FROM OrderEntity o")
    double getAverageOrderValue();

    @Query("SELECT o.orderStatus, COUNT(o) as statusCount FROM OrderEntity o GROUP BY o.orderStatus ORDER BY statusCount DESC")
    List<Object[]> getMostCommonOrderStatus(Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE DATE(o.createdAt) = CURRENT_DATE")
    List<OrderEntity> findOrdersCreatedToday();

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt >= :weekStart AND o.createdAt < :weekEnd")
    List<OrderEntity> findOrdersCreatedThisWeek(@Param("weekStart") LocalDateTime weekStart, @Param("weekEnd") LocalDateTime weekEnd);

    @Query("SELECT o FROM OrderEntity o WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE) AND MONTH(o.createdAt) = MONTH(CURRENT_DATE)")
    List<OrderEntity> findOrdersCreatedThisMonth();

    @Query("SELECT o FROM OrderEntity o WHERE o.orderNumber LIKE %:keyword% OR o.customer.customerName LIKE %:keyword%")
    List<OrderEntity> searchOrders(@Param("keyword") String keyword);

    @Query("UPDATE OrderEntity o SET o.orderStatus = :newStatus WHERE o.orderId IN :orderIds")
    int bulkUpdateOrderStatus(@Param("orderIds") List<Integer> orderIds, @Param("newStatus") OrderStatus newStatus);
}
