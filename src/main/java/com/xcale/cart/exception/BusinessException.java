package com.xcale.cart.exception;

/**
 * Exception used by the application to handle bussines validations
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}
