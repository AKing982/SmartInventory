package org.example.smartinventory.service;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.model.OrderStatus;
import org.example.smartinventory.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderEntity testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new OrderEntity();
        testOrder.setOrderId(1);
        testOrder.setOrderNumber("ORD-001");
        testOrder.setOrderStatus(OrderStatus.PENDING);
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setTotalAmount(BigDecimal.valueOf(100.0));
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));
        Collection<OrderEntity> result = orderService.findAll();
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testSave() {
        orderService.save(testOrder);
        verify(orderRepository).save(testOrder);
    }

    @Test
    void testDelete() {
        orderService.delete(testOrder);
        verify(orderRepository).delete(testOrder);
    }

    @Test
    void testFindById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        Optional<OrderEntity> result = orderService.findById(1L);
        assertThat(result).isPresent().contains(testOrder);
    }

    @Test
    void testFindAllById() {
        when(orderRepository.findAllById(List.of(1L))).thenReturn(List.of(testOrder));
        Collection<OrderEntity> result = orderService.findAllById(List.of(1L));
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testCreateOrder() {

        orderService.createOrder(testOrder);

        verify(orderRepository).save(testOrder);
    }

    @Test
    void testCreateOrders() {
        fail();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        Optional<OrderEntity> result = orderService.getOrderById(1);
        assertThat(result).isPresent().contains(testOrder);
    }

    @Test
    void testGetOrderByOrderNumber() {
        when(orderRepository.findByOrderNumber("ORD-001")).thenReturn(Optional.of(testOrder));
        Optional<OrderEntity> result = orderService.getOrderByOrderNumber("ORD-001");
        assertThat(result).isPresent().contains(testOrder);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getAllOrders();
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetAllOrdersPaginated() {
        Page<OrderEntity> page = new PageImpl<>(List.of(testOrder));
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<OrderEntity> result = orderService.getAllOrdersPaginated(PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1).contains(testOrder);
    }

//    @Test
//    void testUpdateOrder() {
//        when(orderRepository.save(any(OrderEntity.class))).thenReturn(testOrder);
//        Optional<OrderEntity> result = orderService.updateOrder(testOrder);
//        assertThat(result).isPresent().contains(testOrder);
//    }
//
//    @Test
//    void testUpdateOrderStatus() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
//        when(orderRepository.save(any(OrderEntity.class))).thenReturn(testOrder);
//        Optional<OrderEntity> result = orderService.updateOrderStatus(1, OrderStatus.DELIVERED);
//        assertThat(result).isPresent();
//        assertThat(result.get().getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
//    }

    @Test
    void testSearchOrders() {
        when(orderRepository.searchOrders("ORD")).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.searchOrders("ORD");
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetOrdersByStatus() {
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getOrdersByStatus(OrderStatus.PENDING);
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetOrdersByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        when(orderRepository.findByDateRange(start, end)).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getOrdersByDateRange(start, end);
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetOrderCount() {
        when(orderRepository.count()).thenReturn(1L);
        long result = orderService.getOrderCount();
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void testGetOrderCountByStatus() {
        when(orderRepository.countByStatus(OrderStatus.PENDING)).thenReturn(1L);
        long result = orderService.getOrderCountByStatus(OrderStatus.PENDING);
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void testIsOrderNumberUnique() {
        when(orderRepository.findByOrderNumber("ORD-001")).thenReturn(Optional.empty());
        boolean result = orderService.isOrderNumberUnique("ORD-001");
        assertThat(result).isTrue();
    }

//    @Test
//    void testGetTotalOrderValue() {
//        when(orderRepository.getTotalOrderValue()).thenReturn(100.0);
//        double result = orderService.getTotalOrderValue();
//        assertThat(result).isEqualTo(100.0);
//    }

    @Test
    void testGetRecentOrders() {
        when(orderRepository.findRecentOrders(any(Pageable.class))).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getRecentOrders(5);
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetHighValueOrders() {
        when(orderRepository.findHighValueOrders(50.0)).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getHighValueOrders(50.0);
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetOrdersWithoutNotes() {
        when(orderRepository.findOrdersWithoutNotes()).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getOrdersWithoutNotes();
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testGetAverageOrderValue() {
        when(orderRepository.getAverageOrderValue()).thenReturn(100.0);
        double result = orderService.getAverageOrderValue();
        assertThat(result).isEqualTo(100.0);
    }

//    @Test
//    void testGetMostCommonOrderStatus() {
//        when(orderRepository.getMostCommonOrderStatus(any(Pageable.class)))
//                .thenReturn((List<Object[]>) List.of(new Object[]{OrderStatus.PENDING, 5L}));
//        OrderStatus result = orderService.getMostCommonOrderStatus();
//        assertThat(result).isEqualTo(OrderStatus.PENDING);
//    }

    @Test
    void testGetOrdersCreatedToday() {
        when(orderRepository.findOrdersCreatedToday(any(Date.class))).thenReturn(List.of(testOrder));
        List<OrderEntity> result = orderService.getOrdersCreatedToday();
        assertThat(result).hasSize(1).contains(testOrder);
    }

    @Test
    void testBulkUpdateOrderStatus() {
        when(orderRepository.bulkUpdateOrderStatus(anyList(), any(OrderStatus.class))).thenReturn(1);
        int result = orderService.bulkUpdateOrderStatus(List.of(1), OrderStatus.DELIVERED);
        assertThat(result).isEqualTo(1);
    }
}