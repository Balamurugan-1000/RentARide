package com.justcode.vehicleSharing.handler;

public class InvalidActivationCodeException extends RuntimeException {
    public InvalidActivationCodeException(String message) {
        super(message);
    }
}
