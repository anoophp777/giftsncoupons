package com.giftsncoupons.cart.application.exception;

public class CheckoutException extends RuntimeException{
    public CheckoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
