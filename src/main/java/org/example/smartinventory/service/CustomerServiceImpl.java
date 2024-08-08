package org.example.smartinventory.service;

import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.example.smartinventory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void save(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
    }

    @Override
    public void delete(CustomerEntity customerEntity) {
        customerRepository.delete(customerEntity);
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Collection<CustomerEntity> findAllById(Iterable<Long> ids) {
        return customerRepository.findAllById(ids);
    }

    @Override
    public void createCustomer(CustomerEntity customer) {
        customerRepository.save(customer);
    }

    @Override
    public void createCustomers(List<CustomerEntity> customers) {
        customers.forEach(this::createCustomer);
    }

    @Override
    public Page<CustomerEntity> getAllCustomersPaginated(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<CustomerEntity> getCustomerByEmail(String email) {
        return customerRepository.findByCustomerEmail(email);
    }

    @Override
    public List<CustomerEntity> getCustomersByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    @Override
    public List<CustomerEntity> getCustomersAddedAfterDate(LocalDate date) {
        return customerRepository.getCustomersAddedAfterDate(date);
    }

    @Override
    public int updateCustomer(CustomerEntity customer) {
        return 0;
    }

    @Override
    public int updateCustomerEmail(int customerId, String newEmail) {
        return customerRepository.updateCustomerEmail(customerId, newEmail);
    }

    @Override
    public int updateCustomerPhone(int customerId, String newPhone) {
        return customerRepository.updateCustomerPhone(customerId, newPhone);
    }

    @Override
    public int updateCustomerAddress(int customerId, String newAddress) {
        return customerRepository.updateCustomerAddress(customerId, newAddress);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.deleteCustomerById(customerId);
    }

    @Override
    public void deleteCustomers(List<Integer> customerIds) {
        customerIds.forEach(this::deleteCustomer);
    }

    @Override
    public List<CustomerEntity> searchCustomers(String keyword) {
        return List.of();
    }

    @Override
    public long getCustomerCount() {
        return customerRepository.countCustomers();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return false;
    }

    @Override
    public boolean isPhoneUnique(String phone) {
        return false;
    }

    @Override
    public List<OrderEntity> getCustomerOrders(int customerId) {
        return customerRepository.findCustomerOrders(customerId);
    }

    @Override
    public Optional<CustomerEntity> addOrderToCustomer(int customerId, OrderEntity order) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerEntity> removeOrderFromCustomer(int customerId, int orderId) {
        return Optional.empty();
    }

    @Override
    public BigDecimal getTotalOrderValueForCustomer(int customerId) {
        return customerRepository.findTotalOrderValueForCustomer(customerId);
    }

    @Override
    public int getOrderCountForCustomer(int customerId) {
        return customerRepository.countOrdersByCustomerId(customerId);
    }

    @Override
    public List<CustomerEntity> getTopCustomersByOrderValue(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return customerRepository.findTopCustomersByOrderValue(pageable);
    }

    @Override
    public byte[] exportCustomersToCsv() {
        return new byte[0];
    }

    @Override
    public List<CustomerEntity> importCustomersFromCsv(byte[] csvData) {
        return List.of();
    }

    @Override
    public List<CustomerEntity> getCustomersWithNoOrders() {
        return customerRepository.findCustomersWithNoOrders();
    }

    @Override
    public List<CustomerEntity> getCustomersWithOrdersInLastMonth(LocalDateTime lastMonth) {
        return customerRepository.findCustomersWithOrdersInLastMonth(lastMonth);
    }

    @Override
    public List<CustomerEntity> getAllCustomersSorted(String sortBy, String sortOrder) {
        return List.of();
    }
}
