package com.tcs.banking.service;

import com.tcs.banking.dto.CustomerDTO;
import com.tcs.banking.entity.Customer;
import com.tcs.banking.exception.CustomerNotFoundException;
import com.tcs.banking.exception.DuplicateCustomerException;
import com.tcs.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// @Service marks this as the BUSINESS LOGIC LAYER
// All business rules, validations, and transformations go here
// Controller calls Service. Service calls Repository.

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // ✅ GET ALL CUSTOMERS
    // Java 8 Streams — converting List<Entity> to List<DTO>
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        // Stream API: map each Customer entity → CustomerDTO
        return customers.stream()
                .map(this::convertToDTO)          // method reference
                .collect(Collectors.toList());
    }

    // ✅ GET CUSTOMER BY ID
    public CustomerDTO getCustomerById(String customerId) {
        // Optional API — avoids NullPointerException
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with ID: " + customerId));

        return convertToDTO(customer);
    }

    // ✅ ONBOARD NEW CUSTOMER
    @Transactional  // If anything fails, entire operation rolls back
    public CustomerDTO onboardCustomer(CustomerDTO dto) {

        // Business Rule: Check duplicate email
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateCustomerException(
                    "Customer already exists with email: " + dto.getEmail());
        }

        // Generate unique Customer ID
        String customerId = "CUS" + UUID.randomUUID().toString()
                                        .substring(0, 6).toUpperCase();

        // Generate Account Number
        String accountNumber = dto.getAccountType().substring(0, 3).toUpperCase()
                             + "-" + System.currentTimeMillis() % 1000000;

        // Convert DTO → Entity before saving to DB
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAccountType(dto.getAccountType());
        customer.setAccountNumber(accountNumber);
        customer.setBalance(0.0);
        customer.setStatus("Pending");
        customer.setKycStatus("In Review");
        customer.setOnboardedDate(LocalDate.now());

        // Save to PostgreSQL via JPA
        Customer saved = customerRepository.save(customer);

        return convertToDTO(saved);
    }

    // ✅ GET ACTIVE CUSTOMERS — Java 8 Streams example
    public List<CustomerDTO> getActiveCustomers() {
        return customerRepository.findAll()
                .stream()
                .filter(c -> "Active".equals(c.getStatus()))   // filter active only
                .map(this::convertToDTO)                        // convert to DTO
                .collect(Collectors.toList());
    }

    // ✅ GET HIGH VALUE CUSTOMERS — Streams with sorting
    public List<CustomerDTO> getHighValueCustomers(Double minBalance) {
        return customerRepository.findAll()
                .stream()
                .filter(c -> c.getBalance() != null && c.getBalance() > minBalance)
                .sorted((c1, c2) -> Double.compare(c2.getBalance(), c1.getBalance())) // descending
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ UPDATE KYC STATUS
    @Transactional
    public CustomerDTO updateKycStatus(String customerId, String kycStatus) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found: " + customerId));

        customer.setKycStatus(kycStatus);

        // If KYC verified, activate the customer
        if ("Verified".equals(kycStatus)) {
            customer.setStatus("Active");
        }

        Customer updated = customerRepository.save(customer);
        return convertToDTO(updated);
    }

    // ✅ HELPER: Convert Entity → DTO
    // We never expose the Entity directly — clean architecture practice
    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getKycStatus(),
                customer.getAccountNumber(),
                customer.getAccountType(),
                customer.getBalance(),
                customer.getOnboardedDate() != null ?
                        customer.getOnboardedDate().toString() : null
        );
    }
}
