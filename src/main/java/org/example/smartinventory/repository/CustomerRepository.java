package org.example.smartinventory.repository;

import org.example.smartinventory.entities.CustomerEntity;
import org.example.smartinventory.entities.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>
{
    @Modifying
    @Query("UPDATE CustomerEntity c SET c.customerEmail =:newEmail WHERE c.customerId =:customerId")
    int updateCustomerEmail(@Param("customerId") int customerId, @Param("newEmail") String newEmail);

    @Modifying
    @Query("UPDATE CustomerEntity c SET c.customerPhone =:newPhone WHERE c.customerId =:customerId")
    int updateCustomerPhone(@Param("customerId") int customerId, @Param("newPhone") String newPhone);

    @Query("SELECT o FROM CustomerEntity c JOIN c.orders o WHERE c.customerId =:customerId")
    List<OrderEntity> findCustomerOrders(@Param("customerId") int customerId);

    @Query("SELECT COUNT(c) FROM CustomerEntity c")
    long countCustomers();

    @Query("SELECT c FROM CustomerEntity c WHERE c.customerName =:name")
    List<CustomerEntity> findCustomerByName(@Param("name") String name);

    @Query("SELECT c FROM CustomerEntity c WHERE c.dateAdded >:date")
    List<CustomerEntity> getCustomersAddedAfterDate(@Param("date") LocalDate date);

    @Modifying
    @Query("DELETE FROM CustomerEntity c WHERE c.customerId =:id")
    int deleteCustomerById(@Param("id") int id);

    @Modifying
    @Query("UPDATE CustomerEntity c SET c.customerAddress =:newAddress WHERE c.customerId =:customerId")
    int updateCustomerAddress(@Param("customerId") int customerId, @Param("newAddress") String newAddress);

    @Query("SELECT c FROM CustomerEntity c WHERE c.customerEmail LIKE :email")
    Optional<CustomerEntity> findByCustomerEmail(@Param("email") String email);

//    List<CustomerEntity> searchCustomers(@Param("keyword") String keyword);

//    Boolean existsByCustomerEmail(String customerEmail);
//
//    Boolean existsByCustomerPhone(String customerPhone);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM CustomerEntity c LEFT JOIN c.orders o WHERE c.customerId =:id")
    BigDecimal findTotalOrderValueForCustomer(@Param("id") int customerId);

    @Query("SELECT COUNT(o) FROM CustomerEntity c JOIN c.orders o WHERE c.customerId =:customerId")
    int countOrdersByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT c FROM CustomerEntity c WHERE c.orders IS EMPTY")
    List<CustomerEntity> findCustomersWithNoOrders();

    @Query("SELECT DISTINCT c FROM CustomerEntity c JOIN c.orders o WHERE o.orderDate >=:oneMonthAgo ")
    List<CustomerEntity> findCustomersWithOrdersInLastMonth(@Param("oneMonthAgo") LocalDateTime oneMonthAgo);

    @Query("SELECT c FROM CustomerEntity c JOIN c.orders o GROUP BY c ORDER BY SUM(o.totalAmount) DESC")
    List<CustomerEntity> findTopCustomersByOrderValue(Pageable pageable);

//    List<CustomerEntity> findAllCustomersSorted(@Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
}
