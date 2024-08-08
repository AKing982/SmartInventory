package org.example.smartinventory.repository;

import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.model.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("test")
            .withUsername("alpine")
            .withPassword("alpine");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    private OrderEntity testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new OrderEntity();
        testOrder.setOrderNumber("ORD-001");
        testOrder.setOrderStatus(OrderStatus.PENDING);
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setCreatedAt(LocalDateTime.now());
        testOrder.setTotalAmount(BigDecimal.valueOf(100.0));
        entityManager.persist(testOrder);
        entityManager.flush();
    }

    @Test
    void testFindByOrderNumber() {
        Optional<OrderEntity> found = orderRepository.findByOrderNumber("ORD-001");
        assertThat(found).isPresent();
        assertThat(found.get().getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testFindByStatus() {
        List<OrderEntity> found = orderRepository.findByStatus(OrderStatus.PENDING);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void testFindByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<OrderEntity> found = orderRepository.findByDateRange(start, end);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderDate()).isBetween(start, end);
    }

    @Test
    void testCountByStatus() {
        long count = orderRepository.countByStatus(OrderStatus.PENDING);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testFindRecentOrders() {
        List<OrderEntity> found = orderRepository.findRecentOrders(PageRequest.of(0, 10));
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testFindOrdersWithoutNotes() {
        List<OrderEntity> found = orderRepository.findOrdersWithoutNotes();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getInventoryNotes()).isNull();
    }

    @Test
    void testGetMostCommonOrderStatus() {
        List<Object[]> result = orderRepository.getMostCommonOrderStatus(PageRequest.of(0, 10));
        assertThat(result).hasSize(1);
        assertThat(result.get(0)[0]).isEqualTo(OrderStatus.PENDING);
        assertThat(result.get(0)[1]).isEqualTo(1L);
    }

    @Test
    void testFindOrdersCreatedToday() {
        List<OrderEntity> found = orderRepository.findOrdersCreatedToday(Date.from(Instant.now()));
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testFindOrdersCreatedThisWeek() {
        LocalDateTime weekStart = LocalDateTime.now().minusDays(7);
        LocalDateTime weekEnd = LocalDateTime.now().plusDays(1);
        List<OrderEntity> found = orderRepository.findOrdersCreatedThisWeek(weekStart, weekEnd);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testFindOrdersCreatedThisMonth() {
        List<OrderEntity> found = orderRepository.findOrdersCreatedThisMonth();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testSearchOrders() {
        List<OrderEntity> found = orderRepository.searchOrders("ORD");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getOrderNumber()).isEqualTo("ORD-001");
    }

    @Test
    void testBulkUpdateOrderStatus() {
        int updated = orderRepository.bulkUpdateOrderStatus(List.of(testOrder.getOrderId()), OrderStatus.DELIVERED);
        assertThat(updated).isEqualTo(1);
        OrderEntity updatedOrder = entityManager.find(OrderEntity.class, testOrder.getOrderId());
        assertThat(updatedOrder.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    void testFindHighValueOrders() {
        List<OrderEntity> found = orderRepository.findHighValueOrders(50.0);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTotalAmount()).isGreaterThan(BigDecimal.valueOf(50.0));
    }

    @Test
    void testGetAverageOrderValue() {
        double avgValue = orderRepository.getAverageOrderValue();
        assertThat(avgValue).isEqualTo(100.0);
    }

//    @Test
//    void testFindPendingOrdersForFulfillment() {
//        List<OrderEntity> found = orderRepository.findPendingOrdersForFulfillment(PageRequest.of(0, 10));
//        assertThat(found).hasSize(1);
//        assertThat(found.get(0).getOrderStatus()).isEqualTo(OrderStatus.PENDING);
//    }
}