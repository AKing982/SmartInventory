package org.example.smartinventory.repository;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CustomerRepositoryTest {

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
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private CustomerEntity testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerEntity.builder()
                .customerName("John Doe")
                .customerEmail("newemail@example.com")
                .customerPhone("1234567890")
                .customerAddress("123 Test St")
                .dateAdded(LocalDate.now())
                .build();
        testCustomer = entityManager.persist(testCustomer);
        entityManager.flush();
    }

    @Test
    void updateCustomerEmail()
    {
        int updatedRows = customerRepository.updateCustomerEmail(testCustomer.getCustomerId(), "newemail@example.com");
        assertEquals(updatedRows, 1);

        CustomerEntity updatedCustomer = entityManager.find(CustomerEntity.class, testCustomer.getCustomerId());
        assertThat(updatedCustomer.getCustomerEmail()).isEqualTo("newemail@example.com");
    }

    @Test
    void findCustomerOrders()
    {
        OrderEntity order = new OrderEntity();
        order.setCustomer(testCustomer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(new BigDecimal("100.00"));
        entityManager.persist(order);
        entityManager.flush();

        List<OrderEntity> orders = customerRepository.findCustomerOrders(testCustomer.getCustomerId());
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getTotalAmount()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void findCustomersWithOrdersInLastMonth()
    {
        OrderEntity order = new OrderEntity();
        order.setCustomer(testCustomer);
        order.setOrderDate(LocalDateTime.now().minusDays(15));
        order.setTotalAmount(BigDecimal.valueOf(100.0));
        entityManager.persist(order);
        entityManager.flush();

        List<CustomerEntity> customersWithRecentOrders = customerRepository.findCustomersWithOrdersInLastMonth(LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
        assertThat(customersWithRecentOrders).hasSize(1);
        assertThat(customersWithRecentOrders.get(0).getCustomerId()).isEqualTo(testCustomer.getCustomerId());
    }

    @Test
    void findTopCustomersByOrderValue() {
        OrderEntity order = new OrderEntity();
        order.setCustomer(testCustomer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.valueOf(100.0));
        entityManager.persist(order);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);
        List<CustomerEntity> topCustomers = customerRepository.findTopCustomersByOrderValue(pageable);
        assertThat(topCustomers).hasSize(1);
        assertThat(topCustomers.get(0).getCustomerId()).isEqualTo(testCustomer.getCustomerId());
    }


    @AfterEach
    void tearDown() {
    }
}