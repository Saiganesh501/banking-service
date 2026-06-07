package com.tcs.banking.repository;

import com.tcs.banking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository marks this as the DATA ACCESS LAYER
// JpaRepository gives us free CRUD methods:
//   save(), findById(), findAll(), deleteById() etc.
// Spring Data JPA auto-generates SQL behind the scenes

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Spring Data JPA generates SQL from method name automatically
    // SELECT * FROM customers WHERE customer_id = ?
    Optional<Customer> findByCustomerId(String customerId);

    // SELECT * FROM customers WHERE email = ?
    Optional<Customer> findByEmail(String email);

    // SELECT * FROM customers WHERE status = ?
    List<Customer> findByStatus(String status);

    // SELECT * FROM customers WHERE kyc_status = ?
    List<Customer> findByKycStatus(String kycStatus);

    // Custom JPQL query — when method name is not enough
    @Query("SELECT c FROM Customer c WHERE c.accountType = :type AND c.status = 'Active'")
    List<Customer> findActiveCustomersByAccountType(@Param("type") String accountType);

    // Native SQL query — when we need PostgreSQL specific features
    // This is similar to what Spring JDBC does (raw SQL)
    @Query(value = "SELECT * FROM customers WHERE balance > :amount ORDER BY balance DESC",
           nativeQuery = true)
    List<Customer> findCustomersWithBalanceGreaterThan(@Param("amount") Double amount);

    // Check if customer exists
    boolean existsByCustomerId(String customerId);
    boolean existsByEmail(String email);
}
