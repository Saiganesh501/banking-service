package com.tcs.banking.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

// @Entity maps this Java class to a PostgreSQL table called "customers"
// This is how Hibernate ORM works — Java object <-> Database table

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment ID
    private Long id;

    @Column(name = "customer_id", unique = true, nullable = false)
    private String customerId;         // e.g. CUS001

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;             // Active, Pending, Inactive

    @Column(name = "kyc_status")
    private String kycStatus;          // Verified, In Review, Rejected

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;        // Savings, Current, Fixed Deposit

    @Column(name = "balance")
    private Double balance;

    @Column(name = "onboarded_date")
    private LocalDate onboardedDate;

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public LocalDate getOnboardedDate() { return onboardedDate; }
    public void setOnboardedDate(LocalDate onboardedDate) { this.onboardedDate = onboardedDate; }
}
