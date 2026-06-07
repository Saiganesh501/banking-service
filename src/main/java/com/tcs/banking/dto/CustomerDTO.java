package com.tcs.banking.dto;

// DTO = Data Transfer Object
// We NEVER send the Entity directly to the client
// DTO controls exactly what data goes in and out of the API
// This is a clean architecture practice

public class CustomerDTO {

    private String customerId;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    private String kycStatus;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private String onboardedDate;

    // Used for onboarding request (POST body)
    public CustomerDTO() {}

    public CustomerDTO(String customerId, String fullName, String email,
                       String phone, String status, String kycStatus,
                       String accountNumber, String accountType,
                       Double balance, String onboardedDate) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.kycStatus = kycStatus;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.onboardedDate = onboardedDate;
    }

    // ✅ Getters and Setters
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

    public String getOnboardedDate() { return onboardedDate; }
    public void setOnboardedDate(String onboardedDate) { this.onboardedDate = onboardedDate; }
}
