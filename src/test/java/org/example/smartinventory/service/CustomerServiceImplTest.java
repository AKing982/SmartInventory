package org.example.smartinventory.service;

import jakarta.inject.Inject;
import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.repository.CustomerRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerEntity.builder()
                .customerId(1)
                .customerName("John Doe")
                .customerEmail("john@example.com")
                .customerPhone("1234567890")
                .customerAddress("123 Test St")
                .dateAdded(LocalDate.now())
                .build();
    }

    @Test
    void findAll() {
        List<CustomerEntity> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        Collection<CustomerEntity> result = customerService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    @Test
    void save() {
        customerService.save(testCustomer);

        verify(customerRepository).save(testCustomer);
    }

    @Test
    void delete() {
        customerService.delete(testCustomer);

        verify(customerRepository).delete(testCustomer);
    }

    @Test
    void findById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Optional<CustomerEntity> result = customerService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testCustomer);
    }

    @Test
    void findAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<CustomerEntity> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAllById(ids)).thenReturn(customers);

        Collection<CustomerEntity> result = customerService.findAllById(ids);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    @Test
    void createCustomer() {
       customerService.save(testCustomer);

        verify(customerRepository).save(testCustomer);
    }

    @Test
    void createCustomers() {
        List<CustomerEntity> customers = Arrays.asList(testCustomer);
        customerService.createCustomers(customers);

       verify(customerRepository, times(customers.size())).save(any(CustomerEntity.class));
    }

    @Test
    void getAllCustomersPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerEntity> page = new PageImpl<>(Arrays.asList(testCustomer));
        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<CustomerEntity> result = customerService.getAllCustomersPaginated(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(testCustomer);
    }

    @Test
    void getCustomerByEmail() {
        when(customerRepository.findByCustomerEmail("john@example.com")).thenReturn(Optional.of(testCustomer));

        Optional<CustomerEntity> result = customerService.getCustomerByEmail("john@example.com");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testCustomer);
    }

    @Test
    void getCustomersByName() {
        when(customerRepository.findCustomerByName("John Doe")).thenReturn(Arrays.asList(testCustomer));

        List<CustomerEntity> result = customerService.getCustomersByName("John Doe");

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    @Test
    void getCustomersAddedAfterDate() {
        LocalDate date = LocalDate.now().minusDays(1);
        when(customerRepository.getCustomersAddedAfterDate(date)).thenReturn(Arrays.asList(testCustomer));

        List<CustomerEntity> result = customerService.getCustomersAddedAfterDate(date);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    @Test
    void updateCustomer() {
        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);

        int result = customerService.updateCustomer(testCustomer);

        assertEquals(1, result);
    }

    @Test
    void updateCustomerEmail() {
        when(customerRepository.updateCustomerEmail(1, "newemail@example.com")).thenReturn(1);

        int result = customerService.updateCustomerEmail(1, "newemail@example.com");

        assertEquals(1, result);
    }

    // Add similar tests for updateCustomerPhone and updateCustomerAddress

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer(1);

        verify(customerRepository).deleteCustomerById(1);
    }

//    @Test
//    void deleteCustomers() {
//        List<Integer> ids = Arrays.asList(1, 2);
//
//        customerService.deleteCustomers(ids);
//
//        verify(customerRepository).deleteByCustomerIdIn(ids);
//    }

//    @Test
//    void searchCustomers() {
//        when(customerRepository.searchCustomers("John")).thenReturn(Arrays.asList(testCustomer));
//
//        List<CustomerEntity> result = customerService.searchCustomers("John");
//
//        assertThat(result).hasSize(1);
//        assertThat(result).contains(testCustomer);
//    }

    @Test
    void getCustomerCount() {
        when(customerRepository.count()).thenReturn(1L);

        long result = customerService.getCustomerCount();

        assertThat(result).isEqualTo(1L);
    }

//    @Test
//    void isEmailUnique() {
//        when(customerRepository.existsByCustomerEmail("john@example.com")).thenReturn(false);
//
//        boolean result = customerService.isEmailUnique("john@example.com");
//
//        assertThat(result).isTrue();
//    }
//
//    @Test
//    void isPhoneUnique() {
//        when(customerRepository.existsByCustomerPhone("1234567890")).thenReturn(false);
//
//        boolean result = customerService.isPhoneUnique("1234567890");
//
//        assertThat(result).isTrue();
//    }

    @Test
    void getCustomerOrders() {
        OrderEntity order = new OrderEntity();
        when(customerRepository.findCustomerOrders(1)).thenReturn(Arrays.asList(order));

        List<OrderEntity> result = customerService.getCustomerOrders(1);

        assertThat(result).hasSize(1);
        assertThat(result).contains(order);
    }

    // Add tests for addOrderToCustomer and removeOrderFromCustomer

    @Test
    void getTotalOrderValueForCustomer() {
        when(customerRepository.findCustomerOrders(1)).thenReturn(Arrays.asList(
                new OrderEntity(1, LocalDateTime.now(), BigDecimal.valueOf(100.0), testCustomer),
                new OrderEntity(2, LocalDateTime.now(), BigDecimal.valueOf(200.0), testCustomer)
        ));

        BigDecimal result = customerService.getTotalOrderValueForCustomer(1);

        assertThat(result).isEqualTo(300.0);
    }

    @Test
    void getOrderCountForCustomer() {
        when(customerRepository.countOrdersByCustomerId(1)).thenReturn(2);

        int result = customerService.getOrderCountForCustomer(1);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void getTopCustomersByOrderValue() {
        when(customerRepository.findTopCustomersByOrderValue(any(Pageable.class)))
                .thenReturn(Arrays.asList(testCustomer));

        List<CustomerEntity> result = customerService.getTopCustomersByOrderValue(10);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    // Add tests for exportCustomersToCsv and importCustomersFromCsv

    @Test
    void getCustomersWithNoOrders() {
        when(customerRepository.findCustomersWithNoOrders()).thenReturn(Arrays.asList(testCustomer));

        List<CustomerEntity> result = customerService.getCustomersWithNoOrders();

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

    @Test
    void getCustomersWithOrdersInLastMonth() {
        when(customerRepository.findCustomersWithOrdersInLastMonth(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(testCustomer));

        List<CustomerEntity> result = customerService.getCustomersWithOrdersInLastMonth(LocalDateTime.now().minusMonths(1));

        assertThat(result).hasSize(1);
        assertThat(result).contains(testCustomer);
    }

//    @Test
//    void getAllCustomersSorted() {
//        when(customerRepository.findAllCustomersSorted("name", "asc"))
//                .thenReturn(Arrays.asList(testCustomer));
//
//        List<CustomerEntity> result = customerService.getAllCustomersSorted("name", "asc");
//
//        assertThat(result).hasSize(1);
//        assertThat(result).contains(testCustomer);
//    }

    @AfterEach
    void tearDown() {
    }
}