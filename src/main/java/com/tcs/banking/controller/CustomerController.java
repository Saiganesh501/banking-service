package com.tcs.banking.controller;

import com.tcs.banking.dto.CustomerDTO;
import com.tcs.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody
// Automatically converts Java objects to JSON response
// This is the ENTRY POINT for all HTTP requests

@RestController
@RequestMapping("/api/v1/customers")  // Base URL for all endpoints in this class
public class CustomerController {

    @Autowired
    private CustomerService customerService;  // Dependency Injection

    // ✅ GET ALL CUSTOMERS
    // URL: GET http://localhost:8080/api/v1/customers
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);  // HTTP 200 + JSON body
    }

    // ✅ GET CUSTOMER BY ID
    // URL: GET http://localhost:8080/api/v1/customers/CUS001
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable String customerId) {  // extracts CUS001 from URL

        CustomerDTO customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    // ✅ ONBOARD NEW CUSTOMER
    // URL: POST http://localhost:8080/api/v1/customers/onboard
    // Body: { "fullName": "Sai Ganesh", "email": "sai@email.com", ... }
    @PostMapping("/onboard")
    public ResponseEntity<CustomerDTO> onboardCustomer(
            @RequestBody CustomerDTO customerDTO) {  // JSON body → Java object

        CustomerDTO created = customerService.onboardCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // HTTP 201
    }

    // ✅ GET ACTIVE CUSTOMERS
    // URL: GET http://localhost:8080/api/v1/customers/active
    @GetMapping("/active")
    public ResponseEntity<List<CustomerDTO>> getActiveCustomers() {
        List<CustomerDTO> customers = customerService.getActiveCustomers();
        return ResponseEntity.ok(customers);
    }
//
    // ✅ GET HIGH VALUE CUSTOMERS
    // URL: GET http://localhost:8080/api/v1/customers/high-value?minBalance=100000
    @GetMapping("/high-value")
    public ResponseEntity<List<CustomerDTO>> getHighValueCustomers(
            @RequestParam(defaultValue = "100000") Double minBalance) {  // query param

        List<CustomerDTO> customers = customerService.getHighValueCustomers(minBalance);
        return ResponseEntity.ok(customers);
    }

    // ✅ UPDATE KYC STATUS
    // URL: PUT http://localhost:8080/api/v1/customers/CUS001/kyc
    // Body: { "kycStatus": "Verified" }
    @PutMapping("/{customerId}/kyc")
    public ResponseEntity<CustomerDTO> updateKycStatus(
            @PathVariable String customerId,
            @RequestParam String kycStatus) {

        CustomerDTO updated = customerService.updateKycStatus(customerId, kycStatus);
        return ResponseEntity.ok(updated);
    }
}
