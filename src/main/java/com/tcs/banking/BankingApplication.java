package com.tcs.banking;

import com.tcs.banking.entity.Customer;
import com.tcs.banking.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
        System.out.println("====================================");
        System.out.println("  Banking Platform Started!");
        System.out.println("  URL: http://localhost:8080/api/v1/customers");
        System.out.println("====================================");
    }

    // This runs automatically when app starts
    // Inserts 4 sample customers into PostgreSQL
    @Bean
    public CommandLineRunner loadSampleData(CustomerRepository repo) {
        return args -> {

            // Only insert if table is empty
            if (repo.count() > 0) {
                System.out.println("✅ Data already exists, skipping insert.");
                return;
            }

            Customer c1 = new Customer();
            c1.setCustomerId("CUS001");
            c1.setFullName("Arjun Sharma");
            c1.setEmail("arjun.sharma@email.com");
            c1.setPhone("+91-9876543210");
            c1.setStatus("Active");
            c1.setKycStatus("Verified");
            c1.setAccountNumber("SAV-001234");
            c1.setAccountType("Savings");
            c1.setBalance(125430.50);
            c1.setOnboardedDate(LocalDate.of(2023, 3, 15));
            repo.save(c1);

            Customer c2 = new Customer();
            c2.setCustomerId("CUS002");
            c2.setFullName("Priya Reddy");
            c2.setEmail("priya.reddy@email.com");
            c2.setPhone("+91-9845123456");
            c2.setStatus("Active");
            c2.setKycStatus("Verified");
            c2.setAccountNumber("CUR-005678");
            c2.setAccountType("Current");
            c2.setBalance(892100.00);
            c2.setOnboardedDate(LocalDate.of(2023, 6, 22));
            repo.save(c2);

            Customer c3 = new Customer();
            c3.setCustomerId("CUS003");
            c3.setFullName("Rahul Mehta");
            c3.setEmail("rahul.mehta@email.com");
            c3.setPhone("+91-9712345678");
            c3.setStatus("Pending");
            c3.setKycStatus("In Review");
            c3.setAccountNumber("SAV-009012");
            c3.setAccountType("Savings");
            c3.setBalance(45200.75);
            c3.setOnboardedDate(LocalDate.of(2024, 1, 10));
            repo.save(c3);

            Customer c4 = new Customer();
            c4.setCustomerId("CUS004");
            c4.setFullName("Sneha Patel");
            c4.setEmail("sneha.patel@email.com");
            c4.setPhone("+91-9632145678");
            c4.setStatus("Active");
            c4.setKycStatus("Verified");
            c4.setAccountNumber("FD-003456");
            c4.setAccountType("Fixed Deposit");
            c4.setBalance(500000.00);
            c4.setOnboardedDate(LocalDate.of(2022, 11, 5));
            repo.save(c4);

            System.out.println("✅ 4 sample customers loaded into PostgreSQL!");
        };
    }
}