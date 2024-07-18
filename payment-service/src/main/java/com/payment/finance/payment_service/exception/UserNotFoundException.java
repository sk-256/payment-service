package com.payment.finance.payment_service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String givenEmailNotExist) {
    }
}
