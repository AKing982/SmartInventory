package org.example.smartinventory.service;

import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService extends ServiceModel<CustomerEntity>
{
    // Create
    CustomerEntity createCustomer(CustomerEntity customer);
    List<CustomerEntity> createCustomers(List<CustomerEntity> customers);

    // Read
    Page<CustomerEntity> getAllCustomersPaginated(Pageable pageable);
    Optional<CustomerEntity> getCustomerByEmail(String email);
    List<CustomerEntity> getCustomersByName(String name);
    List<CustomerEntity> getCustomersAddedAfterDate(LocalDate date);

    // Update
    Optional<CustomerEntity> updateCustomer(CustomerEntity customer);
    Optional<CustomerEntity> updateCustomerEmail(int customerId, String newEmail);
    Optional<CustomerEntity> updateCustomerPhone(int customerId, String newPhone);
    Optional<CustomerEntity> updateCustomerAddress(int customerId, String newAddress);

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
    double getTotalOrderValueForCustomer(int customerId);
    int getOrderCountForCustomer(int customerId);
    List<CustomerEntity> getTopCustomersByOrderValue(int limit);

    // Export
    byte[] exportCustomersToCsv();

    // Import
    List<CustomerEntity> importCustomersFromCsv(byte[] csvData);

    // Custom queries
    List<CustomerEntity> getCustomersWithNoOrders();
    List<CustomerEntity> getCustomersWithOrdersInLastMonth();

    // Sorting
    List<CustomerEntity> getAllCustomersSorted(String sortBy, String sortOrder);
}
