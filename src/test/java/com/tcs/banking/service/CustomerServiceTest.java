package com.tcs.banking.service;

import com.tcs.banking.dto.CustomerDTO;
import com.tcs.banking.entity.Customer;
import com.tcs.banking.exception.CustomerNotFoundException;
import com.tcs.banking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class) — enables Mockito in JUnit 5
// @Mock — creates a fake/mock version of CustomerRepository (no real DB needed)
// @InjectMocks — creates CustomerService and injects the mock repository into it

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;  // Fake repository — no DB

    @InjectMocks
    private CustomerService customerService;  // Real service with mock injected

    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        // This runs before EVERY test method
        mockCustomer = new Customer();
        mockCustomer.setCustomerId("CUS001");
        mockCustomer.setFullName("Arjun Sharma");
        mockCustomer.setEmail("arjun@email.com");
        mockCustomer.setStatus("Active");
        mockCustomer.setKycStatus("Verified");
        mockCustomer.setAccountType("Savings");
        mockCustomer.setBalance(125000.0);
        mockCustomer.setOnboardedDate(LocalDate.now());
    }

    // ✅ TEST: getAllCustomers returns list
    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // ARRANGE — set up mock behavior
        when(customerRepository.findAll()).thenReturn(Arrays.asList(mockCustomer));

        // ACT — call the method we are testing
        List<CustomerDTO> result = customerService.getAllCustomers();

        // ASSERT — verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CUS001", result.get(0).getCustomerId());
        assertEquals("Arjun Sharma", result.get(0).getFullName());

        // Verify repository was called exactly once
        verify(customerRepository, times(1)).findAll();
    }

    // ✅ TEST: getCustomerById returns correct customer
    @Test
    void getCustomerById_WhenCustomerExists_ShouldReturnCustomer() {
        // ARRANGE
        when(customerRepository.findByCustomerId("CUS001"))
                .thenReturn(Optional.of(mockCustomer));

        // ACT
        CustomerDTO result = customerService.getCustomerById("CUS001");

        // ASSERT
        assertNotNull(result);
        assertEquals("CUS001", result.getCustomerId());
        assertEquals("Active", result.getStatus());
    }

    // ✅ TEST: getCustomerById throws exception when not found
    @Test
    void getCustomerById_WhenCustomerNotFound_ShouldThrowException() {
        // ARRANGE
        when(customerRepository.findByCustomerId("INVALID"))
                .thenReturn(Optional.empty());

        // ASSERT — expect exception to be thrown
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById("INVALID");
        });
    }

    // ✅ TEST: getActiveCustomers returns only active customers
    @Test
    void getActiveCustomers_ShouldReturnOnlyActiveCustomers() {
        // ARRANGE — one active, one inactive
        Customer inactiveCustomer = new Customer();
        inactiveCustomer.setCustomerId("CUS002");
        inactiveCustomer.setStatus("Inactive");
        inactiveCustomer.setBalance(0.0);

        when(customerRepository.findAll())
                .thenReturn(Arrays.asList(mockCustomer, inactiveCustomer));

        // ACT
        List<CustomerDTO> result = customerService.getActiveCustomers();

        // ASSERT — only 1 active customer returned
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).getStatus());
    }
}
