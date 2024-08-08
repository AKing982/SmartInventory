package org.example.smartinventory.repository;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.model.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
    @Query("SELECT o FROM OrderEntity o WHERE o.orderNumber = :orderNumber")
    Optional<OrderEntity> findByOrderNumber(@Param("orderNumber") String orderNumber);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderStatus = :status")
    List<OrderEntity> findByStatus(@Param("status") OrderStatus status);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderStatus = :status")
    long countByStatus(@Param("status") OrderStatus status);

    @Query("SELECT o FROM OrderEntity o ORDER BY o.orderDate DESC")
    List<OrderEntity> findRecentOrders(Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE o.inventoryNotes IS NULL")
    List<OrderEntity> findOrdersWithoutNotes();

    @Query("SELECT o.orderStatus, COUNT(o) as statusCount FROM OrderEntity o GROUP BY o.orderStatus ORDER BY statusCount DESC")
    List<Object[]> getMostCommonOrderStatus(Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt = :date")
    List<OrderEntity> findOrdersCreatedToday(@Param("date") Date date);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt >= :weekStart AND o.createdAt < :weekEnd")
    List<OrderEntity> findOrdersCreatedThisWeek(@Param("weekStart") LocalDateTime weekStart, @Param("weekEnd") LocalDateTime weekEnd);

    @Query("SELECT o FROM OrderEntity o WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE) AND MONTH(o.createdAt) = MONTH(CURRENT_DATE)")
    List<OrderEntity> findOrdersCreatedThisMonth();

    @Query("SELECT o FROM OrderEntity o WHERE o.orderNumber LIKE %:keyword%")
    List<OrderEntity> searchOrders(@Param("keyword") String keyword);

    @Query("UPDATE OrderEntity o SET o.orderStatus = :newStatus WHERE o.orderId IN :orderIds")
    int bulkUpdateOrderStatus(@Param("orderIds") List<Integer> orderIds, @Param("newStatus") OrderStatus newStatus);

    // Methods that may need modification:

    @Query("SELECT o FROM OrderEntity o WHERE o.totalAmount > :threshold")
    List<OrderEntity> findHighValueOrders(@Param("threshold") double threshold);
    // Consider renaming to findLargeQuantityOrders and changing the query to focus on quantity rather than value

    @Query("SELECT AVG(o.totalAmount) FROM OrderEntity o")
    double getAverageOrderValue();

//    @Query("SELECT o FROM OrderEntity o WHERE o.orderStatus = 'PENDING' ORDER BY o.orderDate ASC")
//    List<OrderEntity> findPendingOrdersForFulfillment(Pageable pageable);
}
