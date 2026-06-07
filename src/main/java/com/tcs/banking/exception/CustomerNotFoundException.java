package com.tcs.banking.exception;

// ✅ Custom Exceptions — clean way to handle errors

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
