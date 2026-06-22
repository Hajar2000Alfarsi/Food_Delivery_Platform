package com.example.Food.Delivery.Platform.Exceptions;

public class InvalidOrderStateException extends RuntimeException{
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
