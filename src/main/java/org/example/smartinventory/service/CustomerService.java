package org.example.smartinventory.service;

import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomerService extends ServiceModel<CustomerEntity>
{
    // Create
    void createCustomer(CustomerEntity customer);
    void createCustomers(List<CustomerEntity> customers);

    // Read
    Page<CustomerEntity> getAllCustomersPaginated(Pageable pageable);
    Optional<CustomerEntity> getCustomerByEmail(String email);
    List<CustomerEntity> getCustomersByName(String name);
    List<CustomerEntity> getCustomersAddedAfterDate(LocalDate date);

    // Update
    int updateCustomer(CustomerEntity customer);
    int updateCustomerEmail(int customerId, String newEmail);
    int updateCustomerPhone(int customerId, String newPhone);
    int updateCustomerAddress(int customerId, String newAddress);

    // Delete
    void deleteCustomer(int customerId);
    void deleteCustomers(List<Integer> customerIds);

    // Search
    List<CustomerEntity> searchCustomers(String keyword);

    // Count
    long getCustomerCount();

    // Validation
    boolean isEmailUnique(String email);
    boolean isPhoneUnique(String phone);

    // Order-related
    List<OrderEntity> getCustomerOrders(int customerId);
    Optional<CustomerEntity> addOrderToCustomer(int customerId, OrderEntity order);
    Optional<CustomerEntity> removeOrderFromCustomer(int customerId, int orderId);

    // Statistics
    BigDecimal getTotalOrderValueForCustomer(int customerId);
    int getOrderCountForCustomer(int customerId);
    List<CustomerEntity> getTopCustomersByOrderValue(int limit);

    // Export
    byte[] exportCustomersToCsv();

    // Import
    List<CustomerEntity> importCustomersFromCsv(byte[] csvData);

    // Custom queries
    List<CustomerEntity> getCustomersWithNoOrders();
    List<CustomerEntity> getCustomersWithOrdersInLastMonth(LocalDateTime lastMonth);

    // Sorting
    List<CustomerEntity> getAllCustomersSorted(String sortBy, String sortOrder);
}
