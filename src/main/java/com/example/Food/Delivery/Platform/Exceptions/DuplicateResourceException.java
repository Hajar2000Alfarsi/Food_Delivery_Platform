package com.example.Food.Delivery.Platform.Exceptions;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
